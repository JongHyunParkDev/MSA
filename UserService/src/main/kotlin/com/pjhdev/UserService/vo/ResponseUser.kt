package com.pjhdev.UserService.vo

import java.time.LocalDateTime

data class ResponseUser (
    val email: String,
    val name: String,
    val createdAt: LocalDateTime?,
    val lastModifiedAt: LocalDateTime?
)