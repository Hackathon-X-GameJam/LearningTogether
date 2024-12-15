package com.us.LearnTogether.Student

import com.us.LearnTogether.Account.Account
import com.us.LearnTogether.Account.AccountRepository
import com.us.LearnTogether.Course.Course
import com.us.LearnTogether.Course.CourseRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val accountRepository: AccountRepository,
    private val courseRepository: CourseRepository

) {
    fun getAllStudents(): List<Student>  {
        val daoList: List<StudentDAO> = studentRepository.findAll().toList()
        val students: MutableList<Student> = mutableListOf()
        for(student in daoList) {
            if(student.id == null) continue
            val result: Student? = getStudentById(student.id)
            if (result != null) students.add(result)
        }
        return students
    }
    fun getStudentById(id: String): Student?{
        val student: StudentDAO = studentRepository.findByIdOrNull(id) ?: return null
        val account: Account = accountRepository.findByIdOrNull(student.account)?: return null
        val course: Course = courseRepository.findByIdOrNull(student.cos)?: return null

        return Student(student.id, account, course, student.semester)
    }

    fun getStudentByUsername(username: String): Student?{
        val student: StudentDAO = studentRepository.findByUsername(username) ?: return null
        if (student.id == null) return null
        return getStudentById(student.id)
    }

    fun save(student: StudentDAO): StudentDAO?= studentRepository.save(student)
}