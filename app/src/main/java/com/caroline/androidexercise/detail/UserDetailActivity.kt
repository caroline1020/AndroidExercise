package com.caroline.androidexercise.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
                    binding.container.visibility = View.VISIBLE
                    it.data.apply {
                        Glide.with(binding.avatar).load(avatarUrl)
                            .circleCrop()
                            .into(binding.avatar)
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

    }

    private fun initView() {
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.elevation = 0f
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_close)
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.container.visibility = View.GONE

    }

    companion object {

        private const val KEY_USERNAME = "KEY_USERNAME"

        fun createIntent(activity: Activity, userId: String): Intent {
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra(KEY_USERNAME, userId)
            return intent
        }
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
