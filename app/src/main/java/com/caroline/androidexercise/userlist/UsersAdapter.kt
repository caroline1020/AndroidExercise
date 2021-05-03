package com.caroline.androidexercise.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caroline.androidexercise.databinding.ItemUserBinding
import com.caroline.androidexercise.network.model.GitHubUser

class UsersAdapter(private val onClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ViewHolder>() {
    private val list: ArrayList<GitHubUser> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClickListener)
    }

    fun setData(newList: ArrayList<GitHubUser>?) {
        list.clear()
        if (newList == null) {
            notifyDataSetChanged()
            return
        }
        list.addAll(newList)
        notifyDataSetChanged()
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
        itemView.setOnClickListener { _ ->
            listener.onItemClick(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ItemUserBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(view)
        }
    }

}