package com.pjhdev.UserService.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.pjhdev.UserService.service.UserService
import com.pjhdev.UserService.vo.RequestLogin
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*

class AuthenticationFilter(
    authenticationManagerParam: AuthenticationManager,
    val userService: UserService,
    val objectMapper: ObjectMapper
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
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        // email
        val userName = (authResult!!.principal as User).username
        val userDto = userService.getUserByEmail(userName)



        super.successfulAuthentication(request, response, chain, authResult)
    }
}