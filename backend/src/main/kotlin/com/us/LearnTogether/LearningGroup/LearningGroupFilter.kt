package com.us.LearnTogether.LearningGroup

import com.us.LearnTogether.Student.Student

data class LearningGroupFilter (var groupName: String?, var owner: String?, var minStartTime: Long?, var maxStartTime: Long?, var minEndTime: Long?, var maxEndTime: Long?, var place: String?, var subject: List<String?>, var freeSlots: Int?, var members: List<Student>?)