package com.pjhdev.apigateway.filter

import com.pjhdev.apigateway.util.logger
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomFilter : AbstractGatewayFilterFactory<CustomFilter.Config>(Config::class.java) {
    val logger = logger()

    override fun apply(config: Config): GatewayFilter {

        return GatewayFilter { exchange, chain ->
            val request: ServerHttpRequest = exchange.request
            val response: ServerHttpResponse = exchange.response

            logger.info("Custom PRE filter: request id -> ${request.id}")

            chain.filter(exchange).then(Mono.fromRunnable {
                logger.info("Custom POST filter: response code -> ${response.statusCode}")
            })
        }
    }

    class Config {

    }
}