package com.caroline.androidexercise.userdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.caroline.androidexercise.R
import com.caroline.androidexercise.viewmodel.UserDetailViewModel


class UserDetailActivity : AppCompatActivity() {
    private val viewModel: UserDetailViewModel by lazy {
        ViewModelProvider(this).get(UserDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        extractUsername()
        setContentView(R.layout.activity_user_detail)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.elevation = 0f
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_close)
        }
    }

    private fun extractUsername() {
        val username = intent.getStringExtra(KEY_USERNAME)
        username?.let {
            viewModel.setUsername(username)
        }
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
