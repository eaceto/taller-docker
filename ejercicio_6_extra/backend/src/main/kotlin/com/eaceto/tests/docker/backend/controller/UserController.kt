package com.eaceto.tests.docker.backend.controller

import com.eaceto.tests.docker.backend.model.CreateUserRequest
import com.eaceto.tests.docker.backend.model.LoginRequest
import com.eaceto.tests.docker.backend.model.User
import com.eaceto.tests.docker.backend.repository.UserRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(val userRepository: UserRepository) {

    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun list(): Mono<List<String>> {
        return userRepository
            .findAll()
            .map {
                it.displayName
            }
            .toList()
            .toMono()
    }

    @PostMapping("/", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody createUserRequest: CreateUserRequest): Boolean {
        if (userRepository.findById(createUserRequest.userName).isPresent) {
            return false
        }
        val user = User(
            createUserRequest.userName,
            createUserRequest.displayName,
            createUserRequest.password,
            Date()
        )

        userRepository.save(user)

        return true
    }

    @PostMapping("/login", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody loginRequest: LoginRequest): Mono<User>{
        return Mono.justOrEmpty(
            userRepository.findById(loginRequest.userName)
            )
            .filter {
                it.password == loginRequest.password
            }
    }
}