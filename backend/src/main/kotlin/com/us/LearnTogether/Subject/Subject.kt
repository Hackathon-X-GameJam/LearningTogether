package com.us.LearnTogether.Subject


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("SUBJECT")
public data class Subject (@Id val id: String?, val subjectName: String, var prof: String )