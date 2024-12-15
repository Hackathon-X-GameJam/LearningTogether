package com.us.LearnTogether.Student

import com.us.LearnTogether.Account.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = ["http://localhost:5173"], maxAge = 3600)
class StudentController(
    private val studentService: StudentService,
    private val accountService: AccountService
) {
    @GetMapping
    fun getStudents() = ResponseEntity.ok(studentService.getAllStudents())

    @PostMapping
    fun post(@RequestBody student: Student): Any {
        val studentDAO: StudentDAO = studentService.save(StudentDAO(student.id, student.account.username, student.cos.id!!, student.semester)) ?: return ResponseEntity.internalServerError()
        val savedDAO: StudentDAO = studentService.save(studentDAO) ?: return ResponseEntity.internalServerError()
        if(savedDAO.id == null) return ResponseEntity.internalServerError()
        accountService.save(student.account)
        val savedStudent: Student? = studentService.getStudentById(savedDAO.id)
        return ResponseEntity.created(URI("/${savedStudent?.id}")).body(savedStudent)
    }

    @GetMapping("/{id}")
    fun getStudent(@PathVariable id: String): ResponseEntity<Student> =
        ResponseEntity.ok(studentService.getStudentById(id))

    @GetMapping("/username/{username}")
    fun getStudentByUsername(@PathVariable username: String): ResponseEntity<Student> =
        ResponseEntity.ok(studentService.getStudentByUsername(username))

}