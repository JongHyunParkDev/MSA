package com.pjhdev.UserService.service

import com.pjhdev.UserService.vo.ResponseOrder
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name="order-service") //application name
interface OrderServiceClient {

    @GetMapping("/{userId}/orders") // url
    fun getOrders(@PathVariable userId: Long): List<ResponseOrder>;
}