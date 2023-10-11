package com.example.ex02_form.data.local

import android.content.Context
import com.example.ex02_form.app.app.Either
import com.example.ex02_form.app.app.errors.ErrorApp
import com.example.ex02_form.app.app.left
import com.example.ex02_form.app.app.right
import com.example.ex02_form.domain.User
import com.google.gson.Gson

class UserXmlLocalDataSource(private val context: Context) {

    val sharedPref = context.getSharedPreferences("UserSaved", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun save(user: User): Either<ErrorApp, User> {
        val editor = sharedPref.edit()

        return try {

            editor.putString(user.name, gson.toJson(user))
            editor.apply()

            user.right()

        } catch (ex: Exception) {
            ErrorApp.UnknowErrorApp.left()
        }
    }

    fun getAll(): Either<ErrorApp, List<User>> {
        val users: MutableList<User> = mutableListOf()

        return try {
            sharedPref.all.forEach {
                users.add(gson.fromJson(it.value as String, User::class.java))
            }
            users.right()

        } catch (ex: Exception) {
            ErrorApp.UnknowErrorApp.left()
        }
    }

    fun delete(id: Int): Either<ErrorApp, Boolean> {

        return try {
            sharedPref.edit().remove(id.toString()).apply()

            true.right()

        } catch (ex: Exception) {
            ErrorApp.UnknowErrorApp.left()
        }


    }
}