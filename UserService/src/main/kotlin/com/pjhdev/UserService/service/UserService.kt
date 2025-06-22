package com.pjhdev.UserService.service

import com.pjhdev.UserService.dto.UserDto
import org.springframework.security.core.userdetails.UserDetailsService


interface UserService: UserDetailsService {
    fun createUser(userDto: UserDto): UserDto
    fun getUserByEmail(email: String): UserDto
    fun getUserByAll(): List<UserDto>
}