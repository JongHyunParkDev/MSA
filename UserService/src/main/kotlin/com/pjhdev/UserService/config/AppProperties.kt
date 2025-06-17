package com.pjhdev.UserService.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("app")
data class AppProperties (
    val message: String,
    val allowIp: String,
    val token: Token
) {
    data class Token(
        val access: Access,
        val refresh: Refresh
    )

    data class Access(
        val expirationTime: Long,
        val secret: String
    )

    data class Refresh(
        val expirationTime: Long,
        val secret: String
    )
}