package com.pjhdev.apigateway.filter

import com.pjhdev.apigateway.util.logger
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.ExpiredJwtException


import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class JwtAuthorizationFilter(var env: Environment) :
    AbstractGatewayFilterFactory<JwtAuthorizationFilter.Config>(Config::class.java) {

    val logger = logger()

    private val jwtSecret: ByteArray = Decoders.BASE64.decode(
        env.getProperty("app.token.access.secret")
            ?: throw IllegalStateException("JWT 시크릿 키(app.token.access.secret)가 설정되지 않았습니다. application.yml 또는 환경 변수를 확인하세요.")
    )
    class Config

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val request = exchange.request
            if (!request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return@GatewayFilter onError(
                    exchange,
                    "No authorization header",
                    HttpStatus.UNAUTHORIZED,
                    "Jwt token must need"
                )
            }

            val authorizationHeader =
                request.headers[HttpHeaders.AUTHORIZATION]!![0]
            val jwt = authorizationHeader.replace("Bearer ", "")

            val validationResult = isJwtValid(jwt, request.path.contextPath().value())

            if (!validationResult.isValid) {
                return@GatewayFilter onError(
                    exchange,
                    "JWT token is not valid: ${validationResult.errorMessage}", // 내부 로깅용 상세 에러
                    HttpStatus.UNAUTHORIZED,
                    validationResult.clientMessage // 클라이언트에게 보낼 메시지
                )
            }

            chain.filter(exchange)
        }
    }

    private fun onError(exchange: ServerWebExchange, err: String, httpStatus: HttpStatus, clientMessage: String): Mono<Void?> {
        val response = exchange.response
        response.setStatusCode(httpStatus)
        logger.error(err)

//        val bytes = "The requested token is invalid.".toByteArray(StandardCharsets.UTF_8)
//        val buffer = exchange.response.bufferFactory().wrap(bytes)
//        return response.writeWith(Flux.just(buffer))

        return response.setComplete();
    }

    data class JwtValidationResult(val isValid: Boolean, val errorMessage: String = "", val clientMessage: String = "")

    private fun isJwtValid(jwt: String, path: String): JwtValidationResult {
        return try {
            // 1. authorization 인증
            val payload = Jwts
                .parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret))
                .build()
                .parseSignedClaims(jwt)
                .payload

            // 2. path 확인
            val role = payload["role"] as String

            if (payload.subject.isNullOrEmpty()) {
                JwtValidationResult(false, "JWT subject is empty.", "Invalid token.")
            }
            else {
                if (role.isEmpty()) {
                    JwtValidationResult(false, "JWT Claim Authority is empty.", "Invalid token.")
                }
                else if (role != "ADMIN" && path.contains("admin")) {
                    JwtValidationResult(false, "Jwt Token doesn't have a authority ", "No authority Jwt Token")
                }
                else {
                    JwtValidationResult(true) // Valid case
                }
            }
        } catch (ex: SignatureException) {
            logger.error("JWT signature validation failed: ${ex.message}")
            JwtValidationResult(false, "JWT signature is invalid.", "Invalid token.")
        } catch (ex: ExpiredJwtException) {
            logger.error("JWT token has expired: ${ex.message}")
            JwtValidationResult(false, "JWT token has expired.", "Token expired. Please log in again.")
        } catch (ex: MalformedJwtException) {
            logger.error("Malformed JWT: ${ex.message}")
            JwtValidationResult(false, "Malformed JWT.", "Invalid token format.")
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT format: ${ex.message}")
            JwtValidationResult(false, "Unsupported JWT format.", "Unsupported token format.")
        } catch (ex: IllegalArgumentException) {
            logger.error("Illegal argument passed during JWT processing: ${ex.message}")
            JwtValidationResult(false, "Illegal argument passed during JWT processing.", "There was an issue processing the token.")
        } catch (ex: Exception) {
            logger.error("Unknown JWT validation error occurred: ${ex.javaClass.simpleName} - ${ex.message}")
            JwtValidationResult(false, "Unknown JWT validation error: ${ex.message}", "An error occurred during token validation.")
        }
    }
}