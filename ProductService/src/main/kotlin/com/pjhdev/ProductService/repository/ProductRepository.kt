package com.pjhdev.ProductService.repository

import com.pjhdev.ProductService.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<ProductEntity, Long> {
}