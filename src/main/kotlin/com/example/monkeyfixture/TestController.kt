package com.example.monkeyfixture

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(private val testService: TestService) {

    @GetMapping("/test")
    fun test(): TestDataClass {
        return testService.test()
    }
}

@Service
class TestService {
    fun test(): TestDataClass {
        return TestDataClass(1, "2", "3", "4")
    }
}

data class TestDataClass(val id: Long, val name: String, val name2: String, val name3: String)
