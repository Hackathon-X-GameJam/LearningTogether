package com.us.LearnTogether.Course

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = ["http://localhost:5173"], maxAge = 3600)
class CourseController(private val courseService: CourseService) {
    @GetMapping
    fun getCourses() = ResponseEntity.ok(courseService.getAllCourses());

    @PostMapping
    fun post(@RequestBody course: Course): ResponseEntity<Course> {
        val savedCourse = courseService.save(course)
        return ResponseEntity.created(URI("/${savedCourse.id}")).body(savedCourse)
    }

    @GetMapping("/{id}")
    fun getCourse(@PathVariable id: String): ResponseEntity<Course> =
        ResponseEntity.ok(courseService.getCourseById(id));
}