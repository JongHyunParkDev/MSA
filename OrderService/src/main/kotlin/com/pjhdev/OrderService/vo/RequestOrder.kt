package com.pjhdev.OrderService.vo

data class RequestOrder(
    val productId: Long,
    val qty: Int,
    val unitPrice: Int
)
