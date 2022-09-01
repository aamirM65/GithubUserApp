package com.dicoding.lesson1.githubuserapp.ui.following

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.lesson1.githubuserapp.R
import com.dicoding.lesson1.githubuserapp.adapter.ListUserAdapter
import com.dicoding.lesson1.githubuserapp.databinding.FragmentFollowingBinding
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import com.dicoding.lesson1.githubuserapp.ui.detail.DetailActivity

class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter
    private var users = arrayListOf<UserResponse>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val listUser = args?.getParcelable<UserResponse>(DetailActivity.EXTRA_USER)
        _binding = FragmentFollowingBinding.bind(view)

        adapter = ListUserAdapter(users)

        binding?.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserResponse) {
                Intent(context, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, data)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        viewModel.setListFollowing(listUser?.username.toString())
        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}