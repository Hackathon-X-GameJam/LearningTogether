package com.us.LearnTogether.Subject

import com.us.LearnTogether.LearningGroup.LearningGroup
import com.us.LearnTogether.Student.Student
import com.us.LearnTogether.SubjectCourse.SubjectCourseRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository,
    private val subjectCourseRepository: SubjectCourseRepository,
) {
    fun getAllSubjects(): List<Subject> = subjectRepository.findAll().toList();

    fun getSubjectById(id: String): Subject? = subjectRepository.findByIdOrNull(id);

    fun save(subject: Subject): Subject = subjectRepository.save(subject)

    fun getSubjectsByCourseId(id: String): List<Subject> {
        val subjectIds: List<String> = subjectCourseRepository.findSubjectsByCourseId(id)
        val subjects: MutableList<Subject> = mutableListOf()
        for (subjectId in subjectIds) {
            val subject: Subject? = subjectRepository.findByIdOrNull(subjectId)
            if (subject != null) subjects.add(subject)
        }
        return subjects
    }
}