package com.pjhdev.apigateway.filter

import com.pjhdev.apigateway.util.logger
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class LoggingFilter : AbstractGatewayFilterFactory<LoggingFilter.Config>(Config::class.java) {
    val logger = logger()

    override fun apply(config: Config): GatewayFilter {

        return OrderedGatewayFilter(
            { exchange, chain ->
                val request: ServerHttpRequest = exchange.request
                val response: ServerHttpResponse = exchange.response

                // 로거 사용 (GlobalFilter 클래스의 logger 인스턴스를 사용한다고 가정)
                logger.info("Logging Filter baseMessage: {}", config.baseMessage)
                if (config.preLogger) {
                    logger.info("Logging PRE Filter: request id -> {}", request.id)
                }

                chain.filter(exchange).then(Mono.fromRunnable {
                    if (config.postLogger) {
                        logger.info("Logging POST Filter: response code -> {}", response.statusCode)
                    }
                })
            },
            Ordered.LOWEST_PRECEDENCE
        )
    }

    data class Config (
        var baseMessage: String,
        var preLogger: Boolean,
        var postLogger: Boolean
    )
}