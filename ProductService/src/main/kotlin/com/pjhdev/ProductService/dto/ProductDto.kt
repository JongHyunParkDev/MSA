package com.pjhdev.ProductService.dto

import com.pjhdev.ProductService.entity.ProductEntity
import com.pjhdev.ProductService.vo.ResponseProduct
import java.time.LocalDateTime

data class ProductDto (
    val id: Long? = null,
    val productName: String,
    val qty: Int,
    val unitPrice: Int,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun fromProductEntity(productEntity: ProductEntity): ProductDto {
            return ProductDto(
                id = productEntity.id,
                productName = productEntity.productName,
                qty = productEntity.stock,
                unitPrice = productEntity.unitPrice,
                createdAt = productEntity.createdAt
            )
        }

        fun fromProductEntityList(productEntities: List<ProductEntity>): List<ProductDto> {
            return productEntities.map { fromProductEntity(it) }
        }
    }

    fun toResponseProduct(): ResponseProduct {

        return ResponseProduct(
            productName = this.productName,
            qty = this.qty,
            unitPrice = this.unitPrice,
            createdAt = this.createdAt
        )
    }
}
