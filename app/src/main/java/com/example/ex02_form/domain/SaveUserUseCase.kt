package com.example.ex02_form.domain

import com.example.ex02_form.app.app.Either
import com.example.ex02_form.app.app.errors.ErrorApp

class SaveUserUseCase(private val repository: UserRepository) {

    operator fun invoke(user: User): Either<ErrorApp, User> {
        return repository.save(user)
    }
}