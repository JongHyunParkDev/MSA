package com.pjhdev.UserService.controller

import com.pjhdev.UserService.config.AppProperties
import com.pjhdev.UserService.dto.UserDto
import com.pjhdev.UserService.service.UserService

import com.pjhdev.UserService.vo.RequestUser
import com.pjhdev.UserService.vo.ResponseUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserController (
    val appProperties: AppProperties,
    val userService: UserService) {

    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }

    @GetMapping("/message")
    fun message(): ResponseEntity<String> {
        return ResponseEntity.ok(appProperties.message)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody requestUser: RequestUser): ResponseEntity<ResponseUser> {
        val userDto = UserDto.fromRequestUser(requestUser)
        val responseUser = userService.createUser(userDto).toResponseUser(false)

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser)
    }

}