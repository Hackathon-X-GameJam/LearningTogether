package com.us.LearnTogether.Account

import com.us.LearnTogether.Account.Account
import com.us.LearnTogether.Student.StudentDAO
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface AccountRepository :  CrudRepository<Account, String> {

    @Modifying
    @Query("INSERT INTO account VALUES (:username, :password, :token)")
    fun saveAsNew(username: String, password: String, token: String)
}