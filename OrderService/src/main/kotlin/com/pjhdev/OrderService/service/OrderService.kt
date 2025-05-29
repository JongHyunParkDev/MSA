package com.pjhdev.OrderService.service

import com.pjhdev.OrderService.dto.OrderDto

interface OrderService {
    fun createOrder(orderDto: OrderDto): OrderDto
    fun getOrderByOrderId(orderId: Long): OrderDto
    fun getOrdersByUserId(userId: Long): List<OrderDto>
}