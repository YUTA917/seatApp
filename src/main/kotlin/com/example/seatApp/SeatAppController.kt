package com.example.seatApp

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["http://localhost:5173"], allowCredentials = "true")
class TodoController(val seatRepository: SeatRepository) {

    @GetMapping("/users")
    fun getUsers(): List<User> {
        return seatRepository.getUsers()
    }

    @PostMapping("/users")
    fun saveUser(@RequestBody userRequest: UserRequest): Int {
        println("--@PostMapping--")
        return seatRepository.postUser(userRequest)
    }

    @DeleteMapping("/users/{name}")
    @CrossOrigin(origins = ["http://localhost:8081"], allowCredentials = "true")
    fun deleteUser(@PathVariable("name") name: String): Int {
        println("--@deleteMapping--")
        println("--name--," + name)
        println("--UserRequest(name)--," + UserRequest(name))
        println("--UserRequest(name).name--," + UserRequest(name).name)

        return seatRepository.deleteUser(UserRequest(name))
    }

    @GetMapping("/seats")
    fun getSeats(): List<Seat> {
        return seatRepository.getSeats()
    }

    @PutMapping("/seats")
    fun saveSeats(@RequestBody seatRequest: SeatRequest): Int {
        println("--@PutMapping--")
        return seatRepository.putSeat(seatRequest)
    }

}


