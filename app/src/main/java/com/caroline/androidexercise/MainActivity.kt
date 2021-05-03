package com.caroline.androidexercise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.caroline.androidexercise.databinding.ActivityMainBinding
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.userlist.OnItemClickListener
import com.caroline.androidexercise.userlist.UsersAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private val adapter = UsersAdapter(object : OnItemClickListener {
        override fun onItemClick(item: GitHubUser) {
            TODO("Not yet implemented")
        }
    })

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = adapter
    }
}