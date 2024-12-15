package com.us.LearnTogether.Account

import com.us.LearnTogether.Student.Student
import com.us.LearnTogether.Student.StudentDAO
import com.us.LearnTogether.Student.StudentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val db: AccountRepository,
    private val studentRepository: StudentRepository
) {
    fun getAllAccounts(): List<Account> = db.findAll().toList();

    fun getAccountById(id: String): Account? = db.findByIdOrNull(id);

    fun save(account: Account): Account? {
        if (studentRepository.findByUsername(account.username) != null) {
            val savedAccount: Account = db.save(account)
            return savedAccount
        }
        if (account.token == null) return null
        db.saveAsNew(account.username, account.password, account.token!!)
        val savedAccount: Account? = db.findByIdOrNull(account.username)
        if (savedAccount == null) return null
        studentRepository.save(StudentDAO(null,  savedAccount.username, "MInf", 1))
        return savedAccount
    }
}