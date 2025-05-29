package com.pjhdev.OrderService.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "tm_order")
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var qty: Int,

    @Column(nullable = false)
    var unitPrice: Int,

    @Column(nullable = false)
    var totalPrice: Int,

    @Column(nullable = false)
    var productId: Long,

    @Column(nullable = false)
    var userId: Long,

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
)
