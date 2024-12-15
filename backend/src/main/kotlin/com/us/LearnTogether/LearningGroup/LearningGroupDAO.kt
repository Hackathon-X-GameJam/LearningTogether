package com.us.LearnTogether.LearningGroup

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.sql.Timestamp

@Table("LEARNINGGROUP")
public data class LearningGroupDAO (@Id val id: String?, var groupName: String, val owner: String, var startTime: Timestamp, var endTime: Timestamp, var place: String, var subject: String?, var maxSize: Int)