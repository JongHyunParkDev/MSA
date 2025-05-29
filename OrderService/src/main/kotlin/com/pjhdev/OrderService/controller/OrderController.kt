package com.pjhdev.OrderService.controller

import com.pjhdev.OrderService.config.AppProperties
import com.pjhdev.OrderService.dto.OrderDto
import com.pjhdev.OrderService.service.OrderService
import com.pjhdev.OrderService.vo.RequestOrder
import com.pjhdev.OrderService.vo.ResponseOrder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class OrderController ( val appProperties: AppProperties,
                        val orderService: OrderService) {
    @GetMapping("/health-check")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("ok")
    }

    @GetMapping("/message")
    fun message(): ResponseEntity<String> {
        return ResponseEntity.ok(appProperties.message)
    }

    @PostMapping("/{userId}/orders")
    fun createOrder(@PathVariable userId: Long,
                    @RequestBody requestOrder: RequestOrder): ResponseEntity<ResponseOrder> {
        val orderDto = OrderDto.fromRequestOrder(requestOrder, userId)

        val responseOrder = orderService.createOrder(orderDto).toResponseOrder()

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseOrder)
    }

    @GetMapping("/{userId}/orders")
    fun getOrderByUserId(@PathVariable userId: Long): ResponseEntity<List<ResponseOrder>> {
        val orderDtoList = orderService.getOrdersByUserId(userId)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(orderDtoList.map { it.toResponseOrder() })
    }
}