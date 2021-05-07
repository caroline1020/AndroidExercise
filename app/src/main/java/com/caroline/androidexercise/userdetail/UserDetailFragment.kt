package com.caroline.androidexercise.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.caroline.androidexercise.R
import com.caroline.androidexercise.databinding.FragmentUserDetailBinding
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.viewmodel.UserDetailViewModel

/**
 * Created by nini on 2021/5/7.
 */
class UserDetailFragment : Fragment() {
    private val viewModel: UserDetailViewModel by lazy {
        ViewModelProvider(requireActivity()).get(UserDetailViewModel::class.java)
    }
    lateinit var binding: FragmentUserDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_user_detail, container, false
        )
        val view: View = binding.root
        initViewBinding()
        registerObservers()
        viewModel.getUserDetail()
        return view
    }

    private fun registerObservers() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.swipeRefreshLayout.isRefreshing = it == View.VISIBLE
        })

        viewModel.result.observe(viewLifecycleOwner, Observer {
            when (it) {
                is HttpResult.Success -> {
                    binding.container.visibility = View.VISIBLE
                    it.data.body()?.let { detail ->
                        Glide.with(binding.avatar).load(detail.avatarUrl)
                            .circleCrop()
                            .into(binding.avatar)
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

    }

    private fun initViewBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.container.visibility = View.GONE
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUserDetail()
        }
    }
}