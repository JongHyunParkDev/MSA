package com.pjhdev.UserService.dto

import com.pjhdev.UserService.entity.UserEntity
import com.pjhdev.UserService.vo.RequestUser
import com.pjhdev.UserService.vo.ResponseUser
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

data class UserDto(
    val name: String,
    val email: String,
    val decryptedPassword: String? = null,
    val encryptedPassword: String? = null,
    var createdAt: LocalDateTime? = null,
    var lastModifiedAt: LocalDateTime? = null,
) {
    companion object {
        fun fromRequestUser(requestUser: RequestUser): UserDto {
            return UserDto(
                email = requestUser.email,
                name = requestUser.name,
                decryptedPassword = requestUser.password,
            )
        }
        fun fromUserEntity(userEntity: UserEntity): UserDto {
            return UserDto(
                email = userEntity.email,
                name = userEntity.name,
                encryptedPassword = userEntity.password,
                createdAt = userEntity.createdAt,
                lastModifiedAt = userEntity.lastModifiedAt
            )
        }
    }

    fun toUserEntity(passwordEncoder: PasswordEncoder): UserEntity {
        return UserEntity(
            email = this.email,
            name = this.name,
            password = passwordEncoder.encode(this.decryptedPassword)
        )
    }

    fun toResponseUser(): ResponseUser {
        return ResponseUser(
            email = this.email,
            name = this.name,
            createdAt = this.createdAt,
            lastModifiedAt = this.lastModifiedAt
        )
    }
}