package com.pjhdev.testservice.controller

import com.pjhdev.testservice.util.CommonUtil
import com.pjhdev.testservice.util.logger
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test-service")
class BaseController {
    val log = logger()

    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }

    @GetMapping("/ip")
    fun getClientIp(request: HttpServletRequest): ResponseEntity<String> {
        val clientIp = CommonUtil.getClientIpAddress(request)
        log.info("Your IP: $clientIp")
        return ResponseEntity.ok("Your IP: $clientIp")
    }

}