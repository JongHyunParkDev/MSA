package com.pjhdev.OrderService.service;

import com.pjhdev.OrderService.dto.OrderDto
import com.pjhdev.OrderService.repository.OrderRepository
import org.springframework.stereotype.Service;

@Service
class OrderServiceImpl (
    val orderRepository: OrderRepository
): OrderService {
    override fun createOrder(orderDto: OrderDto): OrderDto {
        val orderEntity = orderDto.toOrderEntity()

        val savedEntity = orderRepository.save(orderEntity)

        return OrderDto.fromOrderEntity(savedEntity)
    }

    override fun getOrderByOrderId(orderId: Long): OrderDto {
        val orderEntity = orderRepository.findById(orderId)

        return OrderDto.fromOrderEntity(orderEntity.get())
    }

    override fun getOrdersByUserId(userId: Long): List<OrderDto> {
        val orderEntityList = orderRepository.findByUserId(userId)

        return OrderDto.fromOrderEntityList(orderEntityList)
    }

}
