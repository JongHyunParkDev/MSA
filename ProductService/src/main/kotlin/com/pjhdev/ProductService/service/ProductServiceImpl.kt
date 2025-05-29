package com.pjhdev.ProductService.service

import com.pjhdev.ProductService.dto.ProductDto
import com.pjhdev.ProductService.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    val productRepository: ProductRepository
) : ProductService {
    override fun getAllProducts(): List<ProductDto> {
        val productEntityList = productRepository.findAll()

        return ProductDto.fromProductEntityList(productEntityList)
    }
}