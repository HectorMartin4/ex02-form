package com.example.ex02_form.domain

import com.example.ex02_form.app.app.Either
import com.example.ex02_form.app.app.errors.ErrorApp

class DeleteUserUseCase(private val repository: UserRepository) {

    operator fun invoke(id: Int): Either<ErrorApp, Boolean> {
        return repository.delete(id)
    }
}