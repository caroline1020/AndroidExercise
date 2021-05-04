package com.caroline.androidexercise

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caroline.androidexercise.databinding.ActivityMainBinding
import com.caroline.androidexercise.detail.UserDetailActivity
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.userlist.OnItemClickListener
import com.caroline.androidexercise.userlist.UsersAdapter
import com.caroline.androidexercise.viewmodel.UserListViewModel
import com.caroline.androidexercise.widgets.LoadMoreRecyclerViewAdapter


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
            when (it) {
                is HttpResult.Success -> {
                    val hasMoreData = it.data.size == UserListViewModel.PAGE_SIZE
                    usersAdapter.onLoadMoreEnd(it.data, hasMoreData)
                }
                is HttpResult.ApiError -> {
                    Toast.makeText(this, it.apiError, Toast.LENGTH_SHORT).show()
                }
                is HttpResult.HttpError -> {
                    Toast.makeText(this, it.exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun getLastId(): Int {
        return usersAdapter.getLastId()
    }

    private val usersAdapter = UsersAdapter(object : OnItemClickListener {
        override fun onItemClick(item: GitHubUser) {
            startActivity(UserDetailActivity.createIntent(this@MainActivity, item.username))
        }
    }, object : LoadMoreRecyclerViewAdapter.OnLoadMoreListener {
        override fun onLoadMore() {
            viewModel.getUserList(getLastId())
        }

    })

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = usersAdapter

    }
}