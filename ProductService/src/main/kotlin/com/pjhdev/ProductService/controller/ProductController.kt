package com.pjhdev.ProductService.controller

import com.pjhdev.ProductService.service.ProductService
import com.pjhdev.ProductService.vo.ResponseProduct
import com.pjhdev.ProductService.config.AppProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class ProductController ( val appProperties: AppProperties,
                          val productService: ProductService) {
    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }

    @GetMapping("/message")
    fun message(): ResponseEntity<String> {
        return ResponseEntity.ok(appProperties.message)
    }

    @GetMapping("/products")
    fun getProducts(): ResponseEntity<List<ResponseProduct>> {
        val productDtoList = productService.getAllProducts()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(productDtoList.map { it.toResponseProduct() })
    }
}