package com.caroline.androidexercise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caroline.androidexercise.databinding.ActivityMainBinding
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.userlist.OnItemClickListener
import com.caroline.androidexercise.userlist.UsersAdapter
import com.caroline.androidexercise.viewmodel.UserListViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewModel: UserListViewModel by lazy {
        ViewModelProvider(this).get(UserListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        registerObservers()
    }

    private fun registerObservers() {

        viewModel.result.observe(this, Observer {
            when(it){
                is HttpResult.Success->{
                    adapter.setData(it.data)
                }
                is HttpResult.Error->{

                }
                is HttpResult.httpError->{

                }
            }
        })
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