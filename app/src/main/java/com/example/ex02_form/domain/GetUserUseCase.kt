package com.example.ex02_form.domain

import com.example.ex02_form.app.app.Either
import com.example.ex02_form.app.app.errors.ErrorApp

class GetUserUseCase(private val repository: UserRepository) {

    operator fun invoke(): Either<ErrorApp, List<User>> {
        return repository.getAll()
    }
}