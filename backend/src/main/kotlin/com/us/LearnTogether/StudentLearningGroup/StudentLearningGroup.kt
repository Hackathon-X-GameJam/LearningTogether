package com.us.LearnTogether.StudentLearningGroup

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Embedded
import org.springframework.data.relational.core.mapping.Table

@Table("STUDENT_LEARNINGGROUP")
public data class StudentLearningGroup (@Id val id: String?, val studentID : String, val learningGroupID : String)