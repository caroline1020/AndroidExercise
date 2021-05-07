package com.caroline.androidexercise

import android.os.Bundle
import android.view.View
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
                    it.data.body()?.let { list ->
                        val hasMoreData = viewModel.hasNextPage()
                        usersAdapter.onLoadMoreEnd(list, hasMoreData)
                    }
                }
                is HttpResult.ApiError -> {
                    Toast.makeText(this, it.apiError, Toast.LENGTH_SHORT).show()
                }
                is HttpResult.HttpError -> {
                    Toast.makeText(this, it.exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.loading.observe(this, Observer {
            binding.swipeRefreshLayout.isRefreshing = it == View.VISIBLE
        })
    }

    private val usersAdapter = UsersAdapter(object : OnItemClickListener {
        override fun onItemClick(item: GitHubUser) {
            startActivity(UserDetailActivity.createIntent(this@MainActivity, item.username))
        }
    }, object : LoadMoreRecyclerViewAdapter.OnLoadMoreListener {
        override fun onLoadMore() {
            viewModel.loadMoreUsers()
        }

    })

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = usersAdapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.reset()
            usersAdapter.reset()
        }

    }
}