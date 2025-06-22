package com.pjhdev.UserService.vo

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RequestLogin(
    @field:NotBlank(message = "Email cannot be blank")
    @field:Size(min = 2, message = "Email must be at least 2 characters")
    @field:Email
    val email: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 8, max = 20, message = "Passwords must be a minimum of 8 characters and a maximum of 20 characters. ")
    val password: String,
)
