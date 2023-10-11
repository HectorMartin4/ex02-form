package com.example.ex02_form.app.app.errors

sealed class ErrorApp {
    object InternetErrorApp : ErrorApp()
    object DatabaseErrorApp : ErrorApp()
    object UnknowErrorApp : ErrorApp()
}