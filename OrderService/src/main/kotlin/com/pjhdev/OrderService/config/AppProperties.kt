package com.pjhdev.OrderService.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
data class AppProperties (
    val message: String,
) {
}