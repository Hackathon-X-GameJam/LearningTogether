package com.us.LearnTogether.Account

import com.us.LearnTogether.Student.Student
import com.us.LearnTogether.Student.StudentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpServerErrorException.InternalServerError
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = ["http://localhost:5173"], maxAge = 3600)
class AccountController(
    private val accountService: AccountService,
    private val studentService: StudentService
) {
    @GetMapping
    fun getStudents() = ResponseEntity.ok(accountService.getAllAccounts());

    @PostMapping
    fun post(@RequestBody account: AccountTO): Any {
        val accountDAO: Account = Account(account.username, account.password, UUID.randomUUID().toString())
        val savedAccount: Account = accountService.save(accountDAO) ?: return ResponseEntity.internalServerError()
        val response: Student? = studentService.getStudentByUsername(savedAccount.username)
        if (response == null || response.id == null) return ResponseEntity.internalServerError()
        return ResponseEntity.created(URI("/${response.id}")).body(response)
    }

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: String): ResponseEntity<Account> =
        ResponseEntity.ok(accountService.getAccountById(id));

}