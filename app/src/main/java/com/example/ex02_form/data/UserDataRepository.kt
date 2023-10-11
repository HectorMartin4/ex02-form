package com.example.ex02_form.data

import com.example.ex02_form.app.app.Either
import com.example.ex02_form.app.app.errors.ErrorApp
import com.example.ex02_form.data.local.UserXmlLocalDataSource
import com.example.ex02_form.domain.User
import com.example.ex02_form.domain.UserRepository

class UserDataRepository(private val localDataSource: UserXmlLocalDataSource) : UserRepository {

    override fun save(user: User): Either<ErrorApp, User> {
        return localDataSource.save(user)
    }

    override fun getAll(): Either<ErrorApp, List<User>> {
        return localDataSource.getAll()
    }

    /*override fun delete(id: Int): Either<ErrorApp, Boolean> {
        return localDataSource.delete(id)
    }*/
}