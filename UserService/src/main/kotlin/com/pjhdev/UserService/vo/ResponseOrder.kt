package com.pjhdev.UserService.vo

import java.time.LocalDateTime

data class ResponseOrder (
    val id: String,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val createdAt: LocalDateTime,

    val productId: String,
)