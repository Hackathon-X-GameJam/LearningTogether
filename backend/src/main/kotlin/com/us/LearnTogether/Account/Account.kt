package com.us.LearnTogether.Account


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("ACCOUNT")
public data class Account (@Id val username: String, var password: String, var token: String?)