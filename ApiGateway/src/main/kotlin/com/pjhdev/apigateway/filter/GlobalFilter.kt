package com.pjhdev.apigateway.filter

import com.pjhdev.apigateway.util.logger
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class GlobalFilter : AbstractGatewayFilterFactory<GlobalFilter.Config>(Config::class.java) {
    val logger = logger()

    override fun apply(config: Config): GatewayFilter {

        return GatewayFilter { exchange, chain ->
            val request: ServerHttpRequest = exchange.request
            val response: ServerHttpResponse = exchange.response

            logger.info("Global Filter baseMessage: ${config.baseMessage}")

            if (config.preLogger)
                logger.info("Global Filter Start: request id -> ${request.id}");

            chain.filter(exchange).then(Mono.fromRunnable {
                if (config.postLogger)
                    logger.info("Global Filter End: response code -> ${response.statusCode}")
            })
        }
    }

    data class Config (
        var baseMessage: String,
        var preLogger: Boolean,
        var postLogger: Boolean
    )
}