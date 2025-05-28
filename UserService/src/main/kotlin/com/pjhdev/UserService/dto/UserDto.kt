package com.pjhdev.UserService.dto

import com.pjhdev.UserService.entity.UserEntity
import com.pjhdev.UserService.vo.RequestUser
import com.pjhdev.UserService.vo.ResponseUser
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

data class UserDto(
    val id: Long? = null,
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
                decryptedPassword = requestUser.password,
                name = requestUser.name,
            )
        }
        fun fromUserEntity(userEntity: UserEntity): UserDto {
            return UserDto(
                id = userEntity.id,
                email = userEntity.email,
                name = userEntity.name,
                encryptedPassword = userEntity.password,
                createdAt = userEntity.createdAt,
                lastModifiedAt = userEntity.lastModifiedAt
            )
        }

        fun fromUserEntityList(userEntities: List<UserEntity>): List<UserDto> {
            return userEntities.map { fromUserEntity(it) }
        }
    }

    fun toUserEntity(passwordEncoder: PasswordEncoder): UserEntity {
        return UserEntity(
            email = this.email,
            name = this.name,
            password = passwordEncoder.encode(this.decryptedPassword)
        )
    }

    fun toResponseUser(isAdmin: Boolean): ResponseUser {
        var id: Long? = null
        if (isAdmin) id = this.id

        return ResponseUser(
            id = id,
            email = this.email,
            name = this.name,
            createdAt = this.createdAt,
            lastModifiedAt = this.lastModifiedAt
        )
    }
}