package com.pjhdev.UserService.vo

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class ResponseUser (
    val id: Long?,
    val email: String,
    val name: String,
    val createdAt: LocalDateTime?,
    val lastModifiedAt: LocalDateTime?,

    var orders: List<ResponseOrder>? = null
)