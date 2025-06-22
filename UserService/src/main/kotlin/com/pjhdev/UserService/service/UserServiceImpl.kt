package com.pjhdev.UserService.service

import com.pjhdev.UserService.dto.UserDto
import com.pjhdev.UserService.entity.UserEntity
import com.pjhdev.UserService.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl (
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
    ): UserService {

    @Transactional
    override fun createUser(userDto: UserDto): UserDto {
        val userEntity = userDto.toUserEntity(passwordEncoder)

        val savedEntity = userRepository.save(userEntity)

        return UserDto.fromUserEntity(savedEntity)
    }

    override fun getUserByEmail(email: String): UserDto {
        val userEntity = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("DB - User not found $email")

        return UserDto.fromUserEntity(userEntity)
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