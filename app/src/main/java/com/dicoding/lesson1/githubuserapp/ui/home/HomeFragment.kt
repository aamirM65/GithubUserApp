package com.dicoding.lesson1.githubuserapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.lesson1.githubuserapp.adapter.ListUserAdapter
import com.dicoding.lesson1.githubuserapp.databinding.FragmentHomeBinding
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import com.dicoding.lesson1.githubuserapp.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: ListUserAdapter
    private var users = arrayListOf<UserResponse>()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]

        homeViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        homeViewModel.setAllUsers()
        homeViewModel.getAllUsers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}