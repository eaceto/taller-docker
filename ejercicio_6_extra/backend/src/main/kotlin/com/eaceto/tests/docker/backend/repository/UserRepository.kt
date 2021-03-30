package com.eaceto.tests.docker.backend.repository

import com.eaceto.tests.docker.backend.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, String>