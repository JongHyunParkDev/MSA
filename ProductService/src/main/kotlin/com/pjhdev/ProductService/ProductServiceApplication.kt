package com.pjhdev.ProductService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@ConfigurationPropertiesScan("com.pjhdev.ProductService.config")
@EnableDiscoveryClient
class ProductServiceApplication

fun main(args: Array<String>) {
	runApplication<ProductServiceApplication>(*args)
}
