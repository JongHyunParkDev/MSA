package com.pjhdev.UserService.controller.admin

import com.pjhdev.UserService.service.UserService
import com.pjhdev.UserService.vo.ResponseUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminUserController (val userService: UserService) {
    val isAdmin: Boolean = true

    @GetMapping("/users/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<ResponseUser> {
        val userDto = userService.getUserByEmail(email)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userDto.toResponseUser(isAdmin))
    }

    @GetMapping("/users")
    fun getUserAll(): ResponseEntity<List<ResponseUser>> {
        val userDtoList = userService.getUserByAll()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userDtoList.map { it.toResponseUser(isAdmin) })
    }
}