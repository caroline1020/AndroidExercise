package com.caroline.androidexercise.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.caroline.androidexercise.R
import com.caroline.androidexercise.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getStringExtra(KEY_USER_ID)
        userId?.let {

        }
        initView()
        registerObservers()
    }

    private fun registerObservers() {


    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        binding.lifecycleOwner = this

    }

    companion object {

        private val KEY_USER_ID = "KEY_USER_ID"

        fun createIntent(activity: Activity, userId: String): Intent {
            val intent = Intent(activity, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.KEY_USER_ID, userId)
            return intent
        }
    }
}
