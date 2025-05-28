package com.pjhdev.UserService.service

import com.pjhdev.UserService.dto.UserDto
import com.pjhdev.UserService.repository.UserRepository
import jakarta.transaction.Transactional
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

        return UserDto.fromUserEntity(userEntity)
    }

    override fun getUserByAll(): List<UserDto> {
        val userEntityList = userRepository.findAll()

        return UserDto.fromUserEntityList(userEntityList)
    }
}