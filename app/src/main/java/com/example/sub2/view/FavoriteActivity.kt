package com.example.sub2.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub2.UserAdapter
import com.example.sub2.database.FavoriteUser
import com.example.sub2.databinding.ActivityFavoriteBinding
import com.example.sub2.model.User
import com.example.sub2.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter : UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    it.putExtra(DetailUserActivity.EXTRA_NAME, data.name)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvUser.layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
            } else {
                rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            }
            rvUser.adapter = adapter
        }

        viewModel.getFavUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
                showLoading(false)
            }
        }

    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id.toString(),
                user.name,
                user.avatar_url
            )
            listUser.add(userMapped)
        }
        return  listUser
    }

    private fun showLoading (state:Boolean){
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}