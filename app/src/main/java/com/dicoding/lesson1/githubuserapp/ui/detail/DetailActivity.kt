package com.dicoding.lesson1.githubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.lesson1.githubuserapp.R
import com.dicoding.lesson1.githubuserapp.adapter.SectionPagerAdapter
import com.dicoding.lesson1.githubuserapp.databinding.ActivityDetailBinding
import com.dicoding.lesson1.githubuserapp.factory.FavoriteViewModelFactory
import com.dicoding.lesson1.githubuserapp.model.UserResponse
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private lateinit var viewModel: DetailViewModel
    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val users = intent.getParcelableExtra<UserResponse>(EXTRA_USER) as UserResponse

        val bundle = Bundle()
        bundle.putParcelable(EXTRA_USER, users)

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        binding?.apply {
            val viewPager: ViewPager2 = viewPager
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs, viewPager) {tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        viewModel = obtainViewModel(this)
        viewModel.isLoading.observe(this, {
            showLoading(it)
        })
        viewModel.setDetailUsers(users.username.toString())
        viewModel.getDetailUsers().observe(this, {
            if (it != null) {
                binding?.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.photo)
                        .apply(RequestOptions().override(120, 120))
                        .into(imgItemPhoto)
                    tvItemName.text = it.name
                    tvItemUsername.text = it.username
                    tvItemCompany.text = it.company
                    tvItemLocation.text = it.location
                    tvItemFollowers.text = it.followers.toString()
                    tvItemFollowing.text = it.following.toString()
                    tvItemRepository.text = it.repository.toString()
                }
            }
        })

        binding?.apply {
            var checked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkUser(users.id)
                withContext(Dispatchers.Main) {
                    checked = if (count > 0) {
                        tbFavorite.isChecked = true
                        true
                    } else {
                        tbFavorite.isChecked = false
                        false
                    }
                }
            }

            tbFavorite.setOnClickListener {
                checked = !checked
                if (checked) {
                    viewModel.addToFavorite(users)
                    make(tbFavorite, getString(R.string.add_success), Snackbar.LENGTH_SHORT).show()
                } else {
                    viewModel.deleteFromFavorite(users.id)
                    make(tbFavorite, getString(R.string.delete_success), Snackbar.LENGTH_SHORT).show()
                }
                tbFavorite.isChecked = checked

            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = users.username
        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}