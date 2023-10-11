package com.example.ex02_form.domain

import com.example.ex02_form.app.app.Either
import com.example.ex02_form.app.app.errors.ErrorApp

interface UserRepository {

    fun save(user: User): Either<ErrorApp, User>
    fun getAll(): Either<ErrorApp, List<User>>

    fun delete(id: Int): Either<ErrorApp, Boolean>

}