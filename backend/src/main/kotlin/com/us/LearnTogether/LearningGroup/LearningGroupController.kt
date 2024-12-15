package com.us.LearnTogether.LearningGroup

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/learningGroup")
@CrossOrigin(origins = ["http://localhost:5173"], maxAge = 3600)
class LearningGroupController(private val learningGroupService: LearningGroupService) {
    @GetMapping
    fun getLearningGroups() = ResponseEntity.ok(learningGroupService.getAllLearningGroups());

    @PostMapping
    fun post(@RequestBody learningGroup: LearningGroup): Any {
        val savedGroup = learningGroupService.save(learningGroup) ?: return ResponseEntity.internalServerError()
        return ResponseEntity.created(URI("/${savedGroup.id}")).body(savedGroup)
    }

    @PostMapping("{learningGroupId}/student/{studentId}")
    fun addStudentById(@PathVariable learningGroupId: String, @PathVariable studentId: String): ResponseEntity<LearningGroup?> {
        return ResponseEntity.ok(learningGroupService.addStudentById(studentId, learningGroupId))
    }

    @GetMapping("/{id}")
    fun getLearningGroup(@PathVariable id: String): ResponseEntity<LearningGroup> =
        ResponseEntity.ok(learningGroupService.getLearningGroupById(id));

    @PostMapping("/filter")
    fun getFilteredLearningGroups(@RequestBody learningGroupFilter: LearningGroupFilter) =
        ResponseEntity.ok(learningGroupService.getFilteredLearningGroups(learningGroupFilter));

    @GetMapping("/filter/empty")
    fun getEmptyLearningGroupFilter() = ResponseEntity.ok(LearningGroupFilter(null, null, null, null, null, null, null, emptyList(), null, null));

    @DeleteMapping("/{id}")
    fun deleteLearningGroup(@PathVariable id: String) = ResponseEntity.ok(learningGroupService.deleteLearningGroupById(id))
}