package com.caroline.androidexercise.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.caroline.androidexercise.R
import com.caroline.androidexercise.databinding.ActivityUserDetailBinding
import com.caroline.androidexercise.network.utils.HttpResult
import com.caroline.androidexercise.viewmodel.UserDetailViewModel

class UserDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding
    private val viewModel: UserDetailViewModel by lazy {
        ViewModelProvider(this).get(UserDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra(KEY_USERNAME)
        username?.let {
            viewModel.getUserDetail(username)
        }
        initView()
        registerObservers()
    }

    private fun registerObservers() {
        viewModel.result.observe(this, Observer {
            when (it) {
                is HttpResult.Success -> {
                    it.data.apply {
                        binding.item = this
                        binding.executePendingBindings()
                        Glide.with(binding.avatar).load(avatarUrl)
                            .circleCrop()
                            .into(binding.avatar)
                    }
                }
                is HttpResult.Error -> {

                }
                is HttpResult.httpError -> {

                }
            }
        })

    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        binding.lifecycleOwner = this

    }

    companion object {

        private const val KEY_USERNAME = "KEY_USERNAME"

        fun createIntent(activity: Activity, userId: String): Intent {
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra(KEY_USERNAME, userId)
            return intent
        }
    }
}
