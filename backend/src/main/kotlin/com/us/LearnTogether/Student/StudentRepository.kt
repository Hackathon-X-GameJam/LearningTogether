package com.us.LearnTogether.Student

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface StudentRepository :  CrudRepository<StudentDAO, String> {

    @Query("SELECT * FROM student WHERE account = :username")
    fun findByUsername(username: String): StudentDAO?
}
