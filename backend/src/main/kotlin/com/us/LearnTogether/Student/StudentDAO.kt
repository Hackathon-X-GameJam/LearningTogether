package com.us.LearnTogether.Student


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("STUDENT")
public data class StudentDAO (@Id val id: String?, var account: String, var cos: String, var semester: Int)