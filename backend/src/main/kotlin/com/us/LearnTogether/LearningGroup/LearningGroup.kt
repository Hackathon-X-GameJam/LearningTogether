package com.us.LearnTogether.LearningGroup

import com.us.LearnTogether.Student.Student
import com.us.LearnTogether.Subject.Subject

data class LearningGroup (val id: String?, var groupName: String, val owner: String, var startTime: Long, var endTime: Long, var place: String, var subject: Subject?, var maxSize: Int, var members: List<Student>)