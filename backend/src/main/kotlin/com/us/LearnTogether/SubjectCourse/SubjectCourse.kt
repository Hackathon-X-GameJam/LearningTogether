package com.us.LearnTogether.SubjectCourse

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("SUBJECTCOURSE")
public data class SubjectCourse (@Id val id: String, val subjectId : String, val courseId : String)