package com.noah.sns.poke

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PokeApplication

fun main(args: Array<String>) {
	runApplication<PokeApplication>(*args)
}
