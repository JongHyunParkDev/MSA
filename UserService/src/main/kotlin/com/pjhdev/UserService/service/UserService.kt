package com.pjhdev.UserService.service

import com.pjhdev.UserService.dto.UserDto

interface UserService {
    fun createUser(userDto: UserDto): UserDto
}