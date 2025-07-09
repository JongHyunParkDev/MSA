package com.pjhdev.UserService.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Component
import jakarta.annotation.PostConstruct

@Component
@RefreshScope
@ConfigurationProperties("app")
class AppProperties {
    var message: String = ""
    var allowIp: String = ""
    var token: Token = Token()

    @PostConstruct
    fun init() {
        println("AppProperties initialized - allowIp: $allowIp, message: $message")
    }

    class Token {
        var access: Access = Access()
        var refresh: Refresh = Refresh()
    }

    class Access {
        var expirationHour: Long = 1
        var secret: String = ""
    }

    class Refresh {
        var expirationHour: Long = 24
        var secret: String = ""
    }
}