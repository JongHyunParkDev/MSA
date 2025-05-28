package com.pjhdev.UserService.exception

import com.pjhdev.UserService.util.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    val logger = logger()
    @ExceptionHandler(ApiException::class)
    fun handleApiException(ex: ApiException): ResponseEntity<ErrorResponse> {
        val code = ex.code
        val message = ex.message ?: "[User-Service] Api Error"

        val errorResponse = ErrorResponse(code, message)

        logger.error("Exception", ex)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("Exception", ex)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(ErrorCodes.INTERNAL_ERROR, "[User-Service] Server Error"))
    }

}

data class ErrorResponse(
    val code: Int,
    val message: String
)