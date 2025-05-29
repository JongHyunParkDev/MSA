package com.pjhdev.OrderService.dto

import com.pjhdev.OrderService.entity.OrderEntity
import com.pjhdev.OrderService.vo.RequestOrder
import com.pjhdev.OrderService.vo.ResponseOrder
import java.time.LocalDateTime

data class OrderDto(
    val id: Long?,
    val qty: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val createdAt: LocalDateTime?,
    val productId: Long,
    val userId: Long
) {
    companion object {
        fun fromRequestOrder(requestOrder: RequestOrder, userId: Long): OrderDto {
            val totalPrice = requestOrder.qty * requestOrder.unitPrice
            return OrderDto(
                id = null,
                productId = requestOrder.productId,
                qty = requestOrder.qty,
                unitPrice = requestOrder.unitPrice,
                totalPrice = totalPrice,
                createdAt = null,
                userId = userId
            )
        }

        fun fromOrderEntity(orderEntity: OrderEntity): OrderDto {
            return OrderDto(
                id = orderEntity.id,
                qty = orderEntity.qty,
                unitPrice = orderEntity.unitPrice,
                totalPrice = orderEntity.totalPrice,
                createdAt = orderEntity.createdAt,

                productId = orderEntity.productId,
                userId = orderEntity.userId
            )
        }

        fun fromOrderEntityList(orderEntities: List<OrderEntity>): List<OrderDto> {
            return orderEntities.map { fromOrderEntity(it) }
        }
    }

    fun toOrderEntity(): OrderEntity {
        return OrderEntity(
            qty = this.qty,
            unitPrice = this.unitPrice,
            totalPrice = this.totalPrice,

            productId = this.productId,
            userId = this.userId
        )
    }

    fun toResponseOrder(): ResponseOrder {
        return ResponseOrder(
            id = this.id ?: throw IllegalStateException("ResponseOrder.id cannot be null"),
            productId = this.productId,
            qty = this.qty,
            unitPrice = this.unitPrice,
            totalPrice = this.totalPrice,
            createdAt = this.createdAt ?: throw IllegalStateException("ResponseOrder.createdAt cannot be null")
        )
    }
}
