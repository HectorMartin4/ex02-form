package com.example.ex02_form.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ex02_form.app.app.Either
import com.example.ex02_form.app.app.errors.ErrorApp
import com.example.ex02_form.app.app.left
import com.example.ex02_form.domain.GetUserUseCase
import com.example.ex02_form.domain.SaveUserUseCase
import com.example.ex02_form.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UserUiState>()
    val uiState: LiveData<UserUiState> = _uiState

    fun saveUser(user: User): Either<ErrorApp, User> {

        return saveUserUseCase(user).fold(
            { responseError(it) },
            { responseSuccess(it) }
        )
    }

    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val feed = getUserUseCase.invoke()
            feed.fold(
                { responseError(it) },
                {
                    _uiState.postValue(
                        UserUiState(
                            user = feed.get()
                        )
                    )
                }
            )
        }
    }

    private fun responseError(errorApp: ErrorApp): Either<ErrorApp, User> = errorApp.left()

    private fun responseSuccess(isOk: User) = saveUserUseCase.invoke(isOk)


    data class UserUiState(
        val errorApp: ErrorApp? = null,
        val isLoading: Boolean = false,
        val user: List<User> = emptyList()
    )
}