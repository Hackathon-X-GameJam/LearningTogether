package com.us.LearnTogether.Course


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

// var hearableBachelor: Boolean, var hearableMaster: String
@Table("COURSE")
public data class Course (@Id val id: String?, val courseName: String )