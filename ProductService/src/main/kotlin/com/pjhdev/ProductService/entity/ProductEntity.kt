package com.pjhdev.ProductService.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "tm_product")
class ProductEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var productName: String,

    @Column(nullable = false)
    var stock: Int,

    @Column(nullable = false)
    var unitPrice: Int,

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime
)