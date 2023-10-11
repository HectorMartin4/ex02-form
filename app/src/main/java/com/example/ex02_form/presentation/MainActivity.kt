package com.example.ex02_form.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ex02_form.R
import com.example.ex02_form.data.UserDataRepository
import com.example.ex02_form.data.local.UserXmlLocalDataSource
import com.example.ex02_form.databinding.ActivityMainBinding
import com.example.ex02_form.domain.GetUserUseCase
import com.example.ex02_form.domain.SaveUserUseCase
import com.example.ex02_form.domain.User
import com.example.ex02_form.presentation.adapter.UserAdapter

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var userAdapter = UserAdapter()

    val viewModels: UserViewModel by lazy {
        UserViewModel(
            SaveUserUseCase(UserDataRepository(UserXmlLocalDataSource(this))),
            GetUserUseCase(UserDataRepository(UserXmlLocalDataSource(this)))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clean()
        setupObservers()
        viewModels.loadUser()
        setupView()
        saveUser()
    }

    fun clean() {
        findViewById<Button>(R.id.button_clean).setOnClickListener {
            findViewById<EditText>(R.id.name).setText("")
            findViewById<EditText>(R.id.surname).setText("")
        }
    }

    fun setupObservers() {

        val observer = Observer<UserViewModel.UserUiState> {}
        viewModels.uiState.observe(this, observer)
    }

    fun setupView() {
        binding?.apply {
            listUsers.apply {
                adapter = userAdapter
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }

    fun saveUser() {
        val name = findViewById<EditText>(R.id.name).text
        val surname = findViewById<EditText>(R.id.surname).text

        findViewById<Button>(R.id.button_save).setOnClickListener {

            UserXmlLocalDataSource(this).save(User((1..100).random(), "$name", "$surname"))
            viewModels.saveUser(User((1..100).random(), "$name", "$surname"))
        }
    }

}