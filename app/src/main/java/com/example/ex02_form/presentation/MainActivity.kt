package com.example.ex02_form.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ex02_form.R
import com.example.ex02_form.data.UserDataRepository
import com.example.ex02_form.data.local.UserXmlLocalDataSource
import com.example.ex02_form.databinding.ActivityMainBinding
import com.example.ex02_form.domain.DeleteUserUseCase
import com.example.ex02_form.domain.GetUserUseCase
import com.example.ex02_form.domain.SaveUserUseCase
import com.example.ex02_form.domain.User
import com.example.ex02_form.presentation.adapter.UserAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    val viewModels: UserViewModel by lazy {
        UserViewModel(
            SaveUserUseCase(UserDataRepository(UserXmlLocalDataSource(this))),
            GetUserUseCase(UserDataRepository(UserXmlLocalDataSource(this))),
            DeleteUserUseCase(UserDataRepository(UserXmlLocalDataSource(this)))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        saveUser()
        clean()
        loadUsers()
    }

    private fun clean() {
        findViewById<Button>(R.id.button_clean).setOnClickListener {
            findViewById<EditText>(R.id.name).setText("")
            findViewById<EditText>(R.id.surname).setText("")
        }
    }

    private fun setupObservers() {
        val observer = Observer<UserViewModel.UserUiState> {
            adapter =
                UserAdapter(dataItems = it.user.toMutableList(), onClickDelete = { position ->
                    it.user.toMutableList().removeAt(position)

                    UserXmlLocalDataSource(this).delete(2)
                    viewModels.deleteUser(position)
                    adapter.notifyItemRemoved(position)
                })
            binding.listUsers.layoutManager = LinearLayoutManager(this)
            binding.listUsers.adapter = adapter
        }
        viewModels.uiState.observe(this, observer)
    }

    private fun saveUser() {
        val name = findViewById<EditText>(R.id.name).text
        val surname = findViewById<EditText>(R.id.surname).text

        findViewById<Button>(R.id.button_save).setOnClickListener {

            UserXmlLocalDataSource(this).save(User((1..100).random(), "$name", "$surname"))
            viewModels.saveUser(User((1..100).random(), "$name", "$surname"))
        }
    }

    private fun loadUsers() {
        binding.buttonLoad.setOnClickListener {
            viewModels.loadUser()
        }
    }


}