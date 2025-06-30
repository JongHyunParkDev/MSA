package com.pjhdev.UserService.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.pjhdev.UserService.config.AppProperties
import com.pjhdev.UserService.service.UserService
import com.pjhdev.UserService.vo.RequestLogin
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

class AuthenticationFilter(
    authenticationManagerParam: AuthenticationManager,
    val userService: UserService,
    val objectMapper: ObjectMapper,
    val appProperties: AppProperties,
): UsernamePasswordAuthenticationFilter() {

    init {
        super.setAuthenticationManager(authenticationManagerParam)
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val creds: RequestLogin = objectMapper.readValue(request?.inputStream, RequestLogin::class.java)

        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                creds.email,
                creds.password,
                Collections.emptyList()))
    }

    // JWT 토큰 발급 access, refresh
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        // email
        val userName = (authResult.principal as User).username
        val userDto = userService.getUserByEmail(userName)

        val now = Instant.now()
        val accessExpiration = now.plus(appProperties.token.access.expirationHour, ChronoUnit.HOURS)
        val refreshExpiration = now.plus(appProperties.token.refresh.expirationHour, ChronoUnit.HOURS)

        val accessToken = Jwts.builder()
            .subject(userDto.id.toString())
            .claim("role", userDto.roleId)
            .expiration(Date.from(accessExpiration))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(appProperties.token.access.secret)))
            .compact()

        val refreshToken = Jwts.builder()
            .subject(userDto.id.toString())
            .expiration(Date.from(refreshExpiration))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(appProperties.token.refresh.secret)))
            .compact()

        response.addHeader("accessToken", accessToken);
        response.addHeader("refreshToken", refreshToken);
    }
}