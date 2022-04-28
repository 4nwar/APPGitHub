package com.example.sub2.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sub2.R
import com.example.sub2.UserAdapter
import com.example.sub2.databinding.FragmentFollowBinding
import com.example.sub2.model.User
import com.example.sub2.viewmodel.FollowersViewModel

class FollowersFragment: Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object:UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(context, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun showLoading (state:Boolean){
        binding.progressBar2.visibility = if (state) View.VISIBLE else View.GONE
    }
}