package com.pjhdev.ProductService.vo

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseProduct(
    val productName: String,
    val qty: Int,
    val unitPrice: Int,
    val createdAt: LocalDateTime
)
