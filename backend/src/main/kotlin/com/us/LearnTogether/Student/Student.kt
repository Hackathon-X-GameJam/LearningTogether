package com.us.LearnTogether.Student


import com.us.LearnTogether.Account.Account
import com.us.LearnTogether.Course.Course
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


data class Student ( val id: String?, var account: Account, var cos: Course, var semester: Int)