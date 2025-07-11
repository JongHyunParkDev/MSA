package com.pjhdev.UserService.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            // Java 8 Time API 지원
            registerModule(JavaTimeModule())

            // 날짜를 문자열로 직렬화 (타임스탬프 대신)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

            // 알 수 없는 프로퍼티 무시
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            // null 값 제외
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
    }

    @Bean
    @LoadBalanced
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}