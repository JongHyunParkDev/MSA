package com.pjhdev.test_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestServiceApplication

fun main(args: Array<String>) {
	runApplication<TestServiceApplication>(*args)
}
