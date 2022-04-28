package com.example.sub2.view

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub2.R
import com.example.sub2.databinding.ActivityMainBinding
import com.example.sub2.viewmodel.MainViewModel
import com.example.sub2.UserAdapter
import com.example.sub2.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel : MainViewModel
    private lateinit var adapter : UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        viewmodel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvUser.layoutManager = GridLayoutManager(this@MainActivity, 2)
            } else {
                rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            }
            rvUser.adapter = adapter
        }

        binding.teQuery.setOnQueryTextListener(onQueryTextListener)


        viewmodel.getSearchUser().observe(this,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            searchuser(query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            searchuser(newText)
            return false
        }
    }

    private fun searchuser(query : String){
        binding.apply {
            viewmodel.setSearchUser(query)
        }
    }

    private fun showLoading (state:Boolean){
        binding.progressBar2.visibility = if (state) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_menu ->{
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}