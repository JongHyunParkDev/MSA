package com.pjhdev.ProductService.service

import com.pjhdev.ProductService.dto.ProductDto

interface ProductService {
    fun getAllProducts(): Iterable<ProductDto>
}