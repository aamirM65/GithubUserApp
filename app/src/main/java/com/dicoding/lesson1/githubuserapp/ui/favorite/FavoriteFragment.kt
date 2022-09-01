package com.dicoding.lesson1.githubuserapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.lesson1.githubuserapp.adapter.ListUserAdapter
import com.dicoding.lesson1.githubuserapp.databinding.FragmentFavoriteBinding
import com.dicoding.lesson1.githubuserapp.factory.FavoriteViewModelFactory
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import com.dicoding.lesson1.githubuserapp.model.local.FavoriteUser
import com.dicoding.lesson1.githubuserapp.ui.detail.DetailActivity

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var adapter: ListUserAdapter
    private var users = arrayListOf<UserResponse>()
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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

        favoriteViewModel = obtainViewModel(this)

        favoriteViewModel.getFavoriteUser().observe(viewLifecycleOwner, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<UserResponse> {
        val listUsers = ArrayList<UserResponse>()
        for (user in users) {
            val userMapped = UserResponse(
                user.id,
                user.username,
                user.photo
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun obtainViewModel(fragment: Fragment): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(fragment.requireActivity().application)
        return ViewModelProvider(fragment, factory)[FavoriteViewModel::class.java]
    }
}