package com.eaceto.tests.docker.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.util.*

@RedisHash("User")
data class User(
    @Id @Indexed val userName: String,
    val displayName: String,
    val password: String,
    val created: Date,
    val lastLogin: Date? = null
)
