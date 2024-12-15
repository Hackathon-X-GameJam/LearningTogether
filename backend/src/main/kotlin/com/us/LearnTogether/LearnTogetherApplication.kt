package com.us.LearnTogether

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import java.sql.Timestamp


@SpringBootApplication
class LearnTogetherApplication

fun main(args: Array<String>) {
	runApplication<LearnTogetherApplication>(*args)
}

@RestController
class MainController {
	@GetMapping("/hello")
	fun sayHello(@RequestParam name: String = " "): String {
		return "Hello, World! $name"
	}

	@GetMapping("/main")
	fun mainPaige(): String {
    	return java.nio.file.Files.readString(java.nio.file.Paths.get("frontend/index.html"))
	}
	@GetMapping("/profile")
	fun showMyProfile(): String {
		return "Show profile"
	}
}

