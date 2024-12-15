package com.us.LearnTogether.SubjectCourse

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository

interface SubjectCourseRepository : CrudRepository<SubjectCourse, String> {
    // returns all Subjects i can hear with my course
    @Query("SELECT subject_id FROM subjectCourse WHERE course_id = :courseId")
    fun findSubjectsByCourseId(courseId: String): List<String>

    // subject hearable by ... courses
    @Query("SELECT course_id FROM subjectCourse WHERE subject_id = :subjectId")
    fun findCoursesBySubjectId(subjectId: String): List<String>
}