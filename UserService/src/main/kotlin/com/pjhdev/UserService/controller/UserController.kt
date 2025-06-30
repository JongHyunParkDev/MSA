package com.pjhdev.UserService.controller

import com.pjhdev.UserService.config.AppProperties
import com.pjhdev.UserService.dto.UserDto
import com.pjhdev.UserService.service.UserService

import com.pjhdev.UserService.vo.RequestUser
import com.pjhdev.UserService.vo.ResponseUser
import jakarta.validation.Valid
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env
import org.springframework.core.env.Environment
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
    val env: Environment,
    val userService: UserService
    ) {

    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<String> {
        val msg = String.format("It's Working in User Service"
                + "\nmessage=" + appProperties.message
                + "\nallowIp=" + appProperties.allowIp
                + "\ntoken.access.secret=" + appProperties.token.access.secret
                + "\ntoken.access.expirationHour=" + appProperties.token.access.expirationHour
                + "\ntoken.refresh.secret=" + appProperties.token.refresh.secret
                + "\ntoken.refresh.expirationHour=" + appProperties.token.refresh.expirationHour
                + "\n\ndynamic env"
                + "\ntoken.access.secret=" + env.getProperty("app.token.access.secret")
                + "\ntoken.access.expirationHour=" + env.getProperty("app.token.access.expiration-hour")
                + "\ntoken.refresh.secret=" + env.getProperty("app.token.refresh.secret")
                + "\ntoken.refresh.expirationHour=" + env.getProperty("app.token.refresh.expiration-hour")
        )

        return ResponseEntity.ok(msg)
//        return ResponseEntity.ok("ok")
    }

    @GetMapping("/message")
    fun message(): ResponseEntity<String> {
        return ResponseEntity.ok(appProperties.message)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody @Valid requestUser: RequestUser): ResponseEntity<ResponseUser> {
        val userDto = UserDto.fromRequestUser(requestUser)
        val responseUser = userService.createUser(userDto).toResponseUser(false)

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser)
    }

}