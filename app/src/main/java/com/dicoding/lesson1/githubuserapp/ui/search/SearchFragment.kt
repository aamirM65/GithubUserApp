package com.dicoding.lesson1.githubuserapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.lesson1.githubuserapp.adapter.ListUserAdapter
import com.dicoding.lesson1.githubuserapp.databinding.FragmentSearchBinding
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import com.dicoding.lesson1.githubuserapp.ui.detail.DetailActivity

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private var rvUser: RecyclerView? = null
    private var users = arrayListOf<UserResponse>()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvUser = binding?.rvUser
        rvUser?.setHasFixedSize(true)

        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SearchViewModel::class.java]

        searchViewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding?.apply {
            rvUser.layoutManager = LinearLayoutManager(context)
            val listUserAdapter = ListUserAdapter(users)
            rvUser.adapter = listUserAdapter

            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserResponse) {
                    Intent(context, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_USER, data)
                        startActivity(it)
                    }
                }
            })

            btnSearch.setOnClickListener {
                searchUser()
            }

            edtSearch.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            searchViewModel.getSearchUsers().observe(viewLifecycleOwner, {
                if (it != null) {
                    listUserAdapter.setList(it)
                }
            })
        }
    }

    private fun searchUser() {
        binding?.apply {
            val query = edtSearch.text.toString()
            if (query.isEmpty()) return
            searchViewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}