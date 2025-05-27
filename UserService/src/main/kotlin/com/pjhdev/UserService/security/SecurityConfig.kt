package com.pjhdev.UserService.security

import com.pjhdev.UserService.config.AppProperties
import com.pjhdev.UserService.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig (
    val userService: UserService,
    val appProperties: AppProperties
) {
    @Bean
    @Throws(Exception::class) // Spring Security의 configure 메서드가 예외를 던지므로 유지합니다.
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http.csrf { csrf -> csrf.disable() }

        // 요청 권한 설정
        http.authorizeHttpRequests { authz ->
            authz
                // 특정 경로에 대한 접근 허용 (인증 없이 접근 가능)
                .requestMatchers(AntPathRequestMatcher("/actuator/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher("/users", "POST")).permitAll()
                .requestMatchers(AntPathRequestMatcher("/message")).permitAll()
                .requestMatchers(AntPathRequestMatcher("/health-check")).permitAll()
                .requestMatchers(AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher("/swagger-resources/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                // 모든 요청 (/**)에 대해 IP 주소 기반 접근 제한.
                .requestMatchers("/**").access(
                    WebExpressionAuthorizationManager(
                        "hasIpAddress('127.0.0.1') or hasIpAddress('::1') or " +
                                "hasIpAddress('${appProperties.allowIp}')"
                    )
                )
                .anyRequest().authenticated()
        }

        // H2 DB 위함.
        http.headers { headers -> headers.frameOptions { frameOptions -> frameOptions.sameOrigin() } }

        return http.build()
    }
}