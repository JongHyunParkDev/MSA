package com.pjhdev.UserService.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.pjhdev.UserService.config.AppProperties
import com.pjhdev.UserService.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userService: UserService,
    private val appProperties: AppProperties,
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun filterChain(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager
    ): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .headers { headers ->
                headers.frameOptions { it.sameOrigin() } // H2 DB 위함
            }
            .authorizeHttpRequests { authz ->
                authz
                    // IP 기반 접근 제한
                    .requestMatchers("/**")
                    .access(createIpAccessManager())
            }
            .addFilter(
                authenticationFilter(authenticationManager)
            )
            .build()
    }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun authenticationFilter(authenticationManager: AuthenticationManager): AuthenticationFilter {
        return AuthenticationFilter(authenticationManager, userService, objectMapper)
    }

    private fun createIpAccessManager(): WebExpressionAuthorizationManager {
        val allowedIps = listOf("127.0.0.1", "::1", appProperties.allowIp)
        val expression = allowedIps.joinToString(" or ") { "hasIpAddress('$it')" }

        return WebExpressionAuthorizationManager(expression)
    }
}