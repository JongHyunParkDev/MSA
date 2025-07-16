package com.pjhdev.UserService.error

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class FeignErrorDecoder:  ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception? {
        when (response.status()) {
            400 -> {}
            404 -> if (methodKey.contains("getOrders")) {
                return ResponseStatusException(
                    HttpStatus.valueOf(response.status()),
                    "User's orders is empty"
                )
            }

            else -> return Exception(response.reason())
        }
        return null
    }
}