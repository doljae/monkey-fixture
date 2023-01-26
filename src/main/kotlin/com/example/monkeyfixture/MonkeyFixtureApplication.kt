package com.example.monkeyfixture

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MonkeyFixtureApplication

fun main(args: Array<String>) {
    runApplication<MonkeyFixtureApplication>(*args)
}
