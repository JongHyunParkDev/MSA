package com.pjhdev.UserService.exception

class ApiException(
    val code: Int,
    message: String? = null
) : RuntimeException(message) {

}