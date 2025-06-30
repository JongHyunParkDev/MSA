package com.pjhdev.UserService

import com.pjhdev.UserService.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
@EnableDiscoveryClient
class UserServiceApplication
fun main(args: Array<String>) {
	runApplication<UserServiceApplication>(*args)
}
