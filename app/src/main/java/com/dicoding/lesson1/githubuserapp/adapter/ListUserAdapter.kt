package com.dicoding.lesson1.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.lesson1.githubuserapp.R
import com.dicoding.lesson1.githubuserapp.databinding.ItemRowUserBinding
import com.dicoding.lesson1.githubuserapp.model.UserResponse

class ListUserAdapter(private var listUser: ArrayList<UserResponse>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(user: UserResponse) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.photo)
                    .apply(RequestOptions().override(55, 55))
                    .into(imgItemPhoto)
                tvItemUsername.text = user.username

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }
    }

    fun setList(users: ArrayList<UserResponse>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponse)
    }
}
