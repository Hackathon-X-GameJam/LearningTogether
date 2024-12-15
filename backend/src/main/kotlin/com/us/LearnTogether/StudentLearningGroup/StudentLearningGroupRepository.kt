package com.us.LearnTogether.StudentLearningGroup

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface StudentLearningGroupRepository : CrudRepository<StudentLearningGroup, String> {
    @Query("SELECT learning_group_id FROM Student_LearningGroup WHERE student_id = :studentId")
    fun findLearningGroupsByStudentId(studentId: String): List<String>

    @Query("SELECT student_id FROM Student_LearningGroup WHERE learning_group_id = :learningGroupId")
    fun findStudentsByLearningGroupId(learningGroupId: String): List<String>

    @Query("SELECT COUNT(*) FROM Student_LearningGroup WHERE student_id = :studentId AND learning_group_id = :learningGroupId")
    fun countByLearningGroupIdAndStudentId(learningGroupId: String, studentId: String): Int

    @Query("SELECT * FROM Student_LearningGroup WHERE student_id = :studentId AND learning_group_id = :learningGroupId")
    fun findByStudentIdAndLearningGroupId(studentId: String, learningGroupId: String): List<StudentLearningGroup>
}