package com.example.sub2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sub2.databinding.ItemRowUserBinding
import com.example.sub2.model.User

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    inner class UserViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(imgItemImg)
                    .load(user.avatar_url)
                    .circleCrop()
                    .into(imgItemImg)
                tvItemUser.text = user.login
                tvItemName.text = user.name
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val list = ArrayList<User>()

    fun setList(user: ArrayList<User>) {
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data : User)
    }
}