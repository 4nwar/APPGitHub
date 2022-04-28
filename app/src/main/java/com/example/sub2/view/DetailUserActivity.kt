package com.example.sub2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.sub2.R
import com.example.sub2.adapter.SectionPagerAdapter
import com.example.sub2.databinding.ActivityDetailUserBinding
import com.example.sub2.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)
        val name = intent.getStringExtra(EXTRA_NAME)


        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        if (username != null) {
            viewModel.setUserDetail(username)
        }
        viewModel.getUserDetail().observe(this) {
            if (it != null){
                binding.apply {
                    tvDetailUsername.text = it.login
                    tvDetailName.text = it.name
                    tvDetailFollower.text = it.followers.toString()
                    tvDetailFollowing.text = it.following.toString()
                    tvLoc.text = it.location
                    tvCom.text = it.company?.length.toString()
                    tvRepo.text = it.public_repos.toString()
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(imgDetailPhoto)
                }
                showLoading(false)
            }
        }

        var isCek = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.cekUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.toggleFav.isChecked = true
                        isCek = true
                    }else{
                        binding.toggleFav.isChecked = false
                        isCek = false
                    }
                }
            }
        }

        binding.toggleFav.setOnClickListener {
            isCek =! isCek
            if (isCek){
                viewModel.addToFav(username,id,avatarUrl,name)
            }else{
                viewModel.delFromFavo(id)
            }
            binding.toggleFav.isChecked = isCek
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
        binding.btnShare.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Halo, ini adalah :\n\n $data")
                type = "text/plain"
            }
            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            }
        }
    }

    private fun showLoading (state:Boolean){
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
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

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
        const val EXTRA_NAME = "extra_name"
    }
}