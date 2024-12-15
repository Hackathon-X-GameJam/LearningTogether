package com.us.LearnTogether.Course

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CourseService(private val db: CourseRepository) {
    fun getAllCourses(): List<Course> = db.findAll().toList();

    fun getCourseById(id: String): Course? = db.findByIdOrNull(id);

    fun save(course: Course): Course = db.save(course)
}