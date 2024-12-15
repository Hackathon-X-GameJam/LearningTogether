package com.us.LearnTogether.LearningGroup

import com.us.LearnTogether.Student.Student
import com.us.LearnTogether.Student.StudentService
import com.us.LearnTogether.StudentLearningGroup.StudentLearningGroup
import com.us.LearnTogether.StudentLearningGroup.StudentLearningGroupRepository
import com.us.LearnTogether.Subject.SubjectRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class LearningGroupService(
    private val learningGroupRepository: LearningGroupRepository,
    private val studentLearningGroupRepository: StudentLearningGroupRepository,
    private val studentService: StudentService,
    private val subjectRepository: SubjectRepository,
) {
    // TBD: properly implement this using jdbc mapping
    fun getAllLearningGroups(): List<LearningGroup>  {
        val daoList: List<LearningGroupDAO> = learningGroupRepository.findAll().toList()
        val learningGroups: MutableList<LearningGroup> = mutableListOf()
        for(learningGroup in daoList) {
            if(learningGroup.id == null) continue
            val result: LearningGroup? = getLearningGroupById(learningGroup.id)
            if (result != null) learningGroups.add(result)
        }
        return learningGroups
    }

    fun getLearningGroupById(id: String): LearningGroup? {
        val learningGroup: LearningGroupDAO = learningGroupRepository.findByIdOrNull(id) ?: return null
        val studentIds: List<String> = studentLearningGroupRepository.findStudentsByLearningGroupId(id)
        val students: MutableList<Student> = mutableListOf()
        for (studentId in studentIds) {
            val student: Student? = studentService.getStudentById(studentId)
            if (student != null) students.add(student)
        }
        return LearningGroup(learningGroup.id, learningGroup.groupName, learningGroup.owner, learningGroup.startTime.time, learningGroup.endTime.time, learningGroup.place, subjectRepository.findByIdOrNull(learningGroup.subject!!), learningGroup.maxSize, students)
    }

    fun getFilteredLearningGroups(learningGroupFilter: LearningGroupFilter): List<LearningGroup> {
        var learningGroupDAOs: MutableList<LearningGroupDAO> = learningGroupRepository.findAll().toMutableList()
        val learningGroups: MutableList<LearningGroup> = mutableListOf()

//        for(property in LearningGroupFilter::class.declaredMemberProperties) {
//            if(learningGroupFilter.groupName == null || property.name == "members") continue
//            learningGroupDAOs = learningGroupDAOs.filter { LearningGroupDAO::class.declaredMemberProperties ==  property.get(learningGroupFilter)}.toMutableList()
//            if(learningGroupDAOs.isEmpty()) return learningGroups
//        }
        // TODO: with reflection
        if(learningGroupFilter.groupName != null)
            learningGroupDAOs = learningGroupDAOs.filter {
                it.groupName.toList().containsAll(learningGroupFilter.groupName!!.toList())
            }.toMutableList()
        if(learningGroupFilter.owner != null)
            learningGroupDAOs = learningGroupDAOs.filter { it.owner == learningGroupFilter.owner!! }.toMutableList()
        if(learningGroupFilter.place != null)
            learningGroupDAOs = learningGroupDAOs.filter { it.place == learningGroupFilter.place!! }.toMutableList()
        if(learningGroupFilter.subject.isNotEmpty())
            learningGroupDAOs = learningGroupDAOs.filter { learningGroupFilter.subject.contains(it.subject) }.toMutableList()
        if(learningGroupFilter.minStartTime != null)
            learningGroupDAOs = learningGroupDAOs.filter { it.startTime.time >= learningGroupFilter.minStartTime!! }.toMutableList()
        if(learningGroupFilter.maxStartTime != null)
            learningGroupDAOs = learningGroupDAOs.filter { it.startTime.time <= learningGroupFilter.maxStartTime!! }.toMutableList()
        if(learningGroupFilter.minEndTime != null)
            learningGroupDAOs = learningGroupDAOs.filter { it.endTime.time >= learningGroupFilter.minEndTime!! }.toMutableList()
        if(learningGroupFilter.maxEndTime != null)
            learningGroupDAOs = learningGroupDAOs.filter { it.endTime.time <= learningGroupFilter.maxEndTime!! }.toMutableList()
        if(learningGroupDAOs.isEmpty()) return learningGroups

        // get learningGroups for further filtering
        for (group in learningGroupDAOs) {
            if (group.id == null) continue
            val learningGroup: LearningGroup? = getLearningGroupById(group.id)
            if (learningGroup != null) learningGroups.add(learningGroup)
        }
        if (learningGroupFilter.members == null || learningGroupFilter.members!!.isEmpty()) return learningGroups
        var result: MutableList<LearningGroup> = mutableListOf()
        for (group in learningGroups) {
            if(group.members.none { it in learningGroupFilter.members!! }) continue
            result.add(group)
        }
        return result
    }

    fun save(learningGroup: LearningGroup): LearningGroup?  {
        val learningGroupDAO = LearningGroupDAO(learningGroup.id, learningGroup.groupName, learningGroup.owner, Timestamp(learningGroup.startTime), Timestamp(learningGroup.endTime), learningGroup.place, learningGroup.subject!!.id, learningGroup.maxSize)
        val learningGroupResult: LearningGroupDAO = learningGroupRepository.save(learningGroupDAO)
        if (learningGroupResult.id == null) return null
        // add owner to members list
        if (studentLearningGroupRepository.countByLearningGroupIdAndStudentId(learningGroupResult.id, learningGroup.owner) == 0)
            studentLearningGroupRepository.save(StudentLearningGroup(null, learningGroup.owner, learningGroupResult.id))
        // remove member
        if (learningGroup.id != null) {
            val oldMembers = studentLearningGroupRepository.findStudentsByLearningGroupId(learningGroupResult.id)
            for (student in oldMembers) {
                if (learningGroup.members.filter{ it.id == student }.isEmpty()) {
                    studentLearningGroupRepository.deleteAll(studentLearningGroupRepository.findByStudentIdAndLearningGroupId(learningGroup.id, student))
                }
            }
        }
        // add members
        for (student in learningGroup.members){
            if (student.id != null && studentLearningGroupRepository.countByLearningGroupIdAndStudentId(learningGroupResult.id, student.id) == 0)
                studentLearningGroupRepository.save(StudentLearningGroup(null, student.id, learningGroupResult.id))
        }

        return getLearningGroupById(learningGroupResult.id)
    }

    fun deleteLearningGroupById(id: String) {
        val studentIds: List<String> = studentLearningGroupRepository.findStudentsByLearningGroupId(id)
        for (student in studentIds) {
            studentLearningGroupRepository.deleteAll(studentLearningGroupRepository.findByStudentIdAndLearningGroupId(student, id))
        }
        learningGroupRepository.deleteById(id)
    }

    // ID validation missing
    fun addStudentById(studentId: String, learningGroupId: String): LearningGroup? {
        if (studentLearningGroupRepository.countByLearningGroupIdAndStudentId(learningGroupId, studentId) == 0)
            studentLearningGroupRepository.save(StudentLearningGroup(null, studentId, learningGroupId))
        return getLearningGroupById(learningGroupId)
    }
}