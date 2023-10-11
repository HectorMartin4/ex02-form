package com.example.ex02_form.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ex02_form.databinding.ViewItemUserBinding
import com.example.ex02_form.domain.User

class UserViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

     val binding = ViewItemUserBinding.bind(view)

    fun render(user: User) {
        binding?.apply {
            itemName.text = user.name
            itemSurname.text = user.surname
        }
    }
}