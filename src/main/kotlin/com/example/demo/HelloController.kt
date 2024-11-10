package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@RestController
@RequestMapping("/api")
class HelloController {

    private val containerId: String by lazy{
        Files.lines(Paths.get("/proc/self/cgroup"))
            .filter { it.contains("docker") }
            .map { it.split("/").last() }
            .findFirst()
            .orElse("unknown")
    }

    @GetMapping("/hello")
    fun hello(): String{
        return "Hello from Container ID : $containerId"
    }
}
