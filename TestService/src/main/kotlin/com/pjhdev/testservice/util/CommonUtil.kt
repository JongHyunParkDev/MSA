package com.pjhdev.testservice.util

import jakarta.servlet.http.HttpServletRequest

object CommonUtil {
    fun getClientIpAddress(request: HttpServletRequest): String? {
        // X-Forwarded-For 헤더 확인: 프록시를 거칠 때 클라이언트의 원본 IP를 포함할 수 있습니다.
        // 콤마로 구분된 여러 IP가 있을 수 있으며, 보통 가장 앞의 IP가 원본 클라이언트 IP입니다.
        var ip = request.getHeader("X-Forwarded-For")

        // X-Forwarded-For가 없거나 유효하지 않은 경우 다른 헤더들을 확인합니다.
        if (ip.isNullOrBlank() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip.isNullOrBlank() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip.isNullOrBlank() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_CLIENT_IP")
        }
        if (ip.isNullOrBlank() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }

        // 위의 헤더들에서도 찾지 못한 경우, 요청의 원격 주소를 사용합니다.
        if (ip.isNullOrBlank() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }

        // X-Forwarded-For 헤더에 여러 IP가 콤마로 구분되어 있는 경우 첫 번째 IP를 가져옵니다.
        // 예를 들어 "IP1, IP2, IP3" 형태일 때 "IP1"을 반환합니다.
        val commaIndex = ip?.indexOf(',')
        if (commaIndex != null && commaIndex > 0) {
            ip = ip.substring(0, commaIndex).trim()
        }

        return ip
    }
}