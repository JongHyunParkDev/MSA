package com.pjhdev.UserService.service

import com.pjhdev.UserService.dto.UserDto
import com.pjhdev.UserService.entity.UserEntity
import com.pjhdev.UserService.repository.UserRepository
import com.pjhdev.UserService.vo.ResponseOrder
import jakarta.transaction.Transactional
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class UserServiceImpl (
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val restTemplate: RestTemplate,
    val env: Environment
    ): UserService {

    @Transactional
    override fun createUser(userDto: UserDto): UserDto {
        val userEntity = userDto.toUserEntity(passwordEncoder)

        val savedEntity = userRepository.save(userEntity)

        return UserDto.fromUserEntity(savedEntity, null)
    }

    override fun getUserById(id: Long): UserDto {
        val userEntity = userRepository.findById(id).get()

        // 1. Communication RestTemplate [MSA]
        val orderUrl = String.format(env.getRequiredProperty("order_service.url"), id)
        val response: ResponseEntity<List<ResponseOrder>> = restTemplate.exchange(orderUrl, HttpMethod.GET, null)
        val orderList: List<ResponseOrder>? = response.body

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