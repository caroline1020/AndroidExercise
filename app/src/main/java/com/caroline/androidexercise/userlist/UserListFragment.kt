package com.caroline.androidexercise.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.caroline.androidexercise.R
import com.caroline.androidexercise.databinding.FragmentUserListBinding
import com.caroline.androidexercise.userdetail.UserDetailActivity
import com.caroline.androidexercise.network.model.GitHubUser
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.viewmodel.UserListViewModel
import com.caroline.androidexercise.widgets.LoadMoreRecyclerViewAdapter

/**
 * Created by nini on 2021/5/7.
 */
class UserListFragment : Fragment() {

    val viewModel: UserListViewModel by lazy {
        ViewModelProvider(requireActivity()).get(UserListViewModel::class.java)
    }
    lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_user_list, container, false
        )
        val view: View = binding.root
        initViewBinding()
        registerObservers()
        return view
    }

    private fun registerObservers() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HttpResult.Success -> {
                    it.data.body()?.let { list ->
                        val hasMoreData = viewModel.hasNextPage()
                        usersAdapter.onLoadMoreEnd(list, hasMoreData)
                    }
                }
                is HttpResult.ApiError -> {
                    Toast.makeText(context, it.apiError, Toast.LENGTH_SHORT).show()
                }
                is HttpResult.HttpError -> {
                    Toast.makeText(context, it.exception.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = it == View.VISIBLE
        })
    }

    private val usersAdapter = UsersAdapter(object : OnItemClickListener {
        override fun onItemClick(item: GitHubUser) {
            startActivity(UserDetailActivity.createIntent(requireActivity(), item.username))
        }
    }, object : LoadMoreRecyclerViewAdapter.OnLoadMoreListener {
        override fun onLoadMore() {
            viewModel.loadMoreUsers()
        }

    })

    private fun initViewBinding() {
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = usersAdapter
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.reset()
            usersAdapter.reset()
        }

    }
}