package com.example.seatApp


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SeatAppApplication

fun main(args: Array<String>) {
    runApplication<SeatAppApplication>(*args)
//    val path = System.getenv("USER")
//    println(path)
}
