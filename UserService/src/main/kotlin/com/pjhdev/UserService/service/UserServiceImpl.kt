package com.pjhdev.UserService.service

import com.pjhdev.UserService.client.OrderServiceClient
import com.pjhdev.UserService.dto.UserDto
import com.pjhdev.UserService.entity.UserEntity
import com.pjhdev.UserService.repository.UserRepository
import com.pjhdev.UserService.util.logger
import com.pjhdev.UserService.vo.ResponseOrder
import feign.FeignException
import jakarta.transaction.Transactional
import org.springframework.core.env.Environment
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserServiceImpl (
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val restTemplate: RestTemplate,
    val env: Environment,
    val orderServiceClient: OrderServiceClient,
    ): UserService {

    val logger = logger()
    @Transactional
    override fun createUser(userDto: UserDto): UserDto {
        val userEntity = userDto.toUserEntity(passwordEncoder)

        val savedEntity = userRepository.save(userEntity)

        return UserDto.fromUserEntity(savedEntity, null)
    }

    override fun getUserById(id: Long): UserDto {
        val userEntity = userRepository.findById(id).get()

        // 1. Communication RestTemplate [MSA]
//        val orderUrl = String.format(env.getRequiredProperty("order_service.url"), id)
//        val response: ResponseEntity<List<ResponseOrder>> = restTemplate.exchange(orderUrl, HttpMethod.GET, null)
//        val orderList: List<ResponseOrder>? = response.body

//            error 처리 코드 #1
//        val orderList: List<ResponseOrder> = runCatching {
//            orderServiceClient.getOrders(id)
//        }.getOrElse { err ->
//            if (err is FeignException) {
//                logger.error("주문 정보를 가져오는데 실패했습니다: ${err.message}")
//            } else {
//                logger.error("예기치 않은 오류 발생: ${err.message}", err)
//                // 다른 종류의 예외이거나, 복구 불가능한 경우 다시 던질 수 있습니다.
//                throw err
//            }
//            emptyList() // 실패 시 orderList에 할당할 기본값 (예: 빈 리스트)
//        }

//        error 처리 코드 #2
        val orderList: List<ResponseOrder> = orderServiceClient.getOrders(id)
        return UserDto.fromUserEntity(userEntity, orderList)
    }

    override fun getUserByEmail(email: String): UserDto {
        val userEntity = userRepository.findByEmail(email)

        return UserDto.fromUserEntity(userEntity, null)
    }

    override fun getUserByAll(): List<UserDto> {
        val userEntityList = userRepository.findAll()

        return UserDto.fromUserEntityList(userEntityList)
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val userEntity: UserEntity = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("DB - User not found $email")

        return User(userEntity.email, userEntity.password,
            true, true, true, true,
            listOf(SimpleGrantedAuthority("ROLE_${userEntity.role.name}"))
        )
    }
}