package com.us.LearnTogether.Subject

import com.us.LearnTogether.Course.CourseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = ["http://localhost:5173"], maxAge = 3600)
class SubjectController(private val subjectService: SubjectService) {
    @GetMapping
    fun getSubjects() = ResponseEntity.ok(subjectService.getAllSubjects());

    @PostMapping
    fun post(@RequestBody subject: Subject): ResponseEntity<Subject> {
        val savedSubject = subjectService.save(subject)
        return ResponseEntity.created(URI("/${savedSubject.id}")).body(savedSubject)
    }

    @GetMapping("/{id}")
    fun getSubject(@PathVariable id: String): ResponseEntity<Subject> =
        ResponseEntity.ok(subjectService.getSubjectById(id));

    @GetMapping("/course/{id}")
    fun getSubjectsByCourseId(@PathVariable id: String): ResponseEntity<List<Subject>> =
        ResponseEntity.ok(subjectService.getSubjectsByCourseId(id));

}