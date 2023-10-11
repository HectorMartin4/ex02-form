package com.example.ex02_form.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ex02_form.R
import com.example.ex02_form.domain.User

class UserAdapter(
    val dataItems: MutableList<User>,
    private val onClickDelete: (Int) -> Unit
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_user, parent, false)

        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = dataItems.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.render(dataItems[position], onClickDelete)
    }
}