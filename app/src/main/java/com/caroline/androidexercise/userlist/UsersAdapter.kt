package com.caroline.androidexercise.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caroline.androidexercise.databinding.ItemLoadMoreBinding
import com.caroline.androidexercise.databinding.ItemUserBinding
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.widgets.LoadMoreRecyclerViewAdapter

class UsersAdapter(
    private val onClickListener: OnItemClickListener,
    onLoadMoreListener: OnLoadMoreListener
) :
    LoadMoreRecyclerViewAdapter<GitHubUser>(onLoadMoreListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADMORE -> {
                LoadMoreViewHolder.from(parent)
            }
            else -> ViewHolder.from(parent)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is ViewHolder) {
            holder.bind(getData()[position], onClickListener)
        }
    }


    fun getLastId(): Int {
        if (getData().isEmpty()) {
            return 0
        }
        return getData().last().id
    }
}

interface OnItemClickListener {
    fun onItemClick(item: GitHubUser)
}

class ViewHolder private constructor(private val binding: ItemUserBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(item: GitHubUser, listener: OnItemClickListener) {
        binding.item = item
        binding.executePendingBindings()
        itemView.setOnClickListener {
            listener.onItemClick(item)
        }

        Glide.with(itemView.context).load(item.avatarUrl)
            .circleCrop()
            .into(binding.icon)
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ItemUserBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(view)
        }
    }

}

class LoadMoreViewHolder private constructor(binding: ItemLoadMoreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): LoadMoreViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ItemLoadMoreBinding.inflate(layoutInflater, parent, false)
            return LoadMoreViewHolder(view)
        }
    }

}