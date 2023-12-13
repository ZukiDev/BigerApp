package com.marzuki.bigerapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.data.network.ApiConfig
import com.marzuki.bigerapp.data.network.ApiService
import com.marzuki.bigerapp.databinding.ActivityMainBinding
import com.marzuki.bigerapp.view.ViewModelFactory
import com.marzuki.bigerapp.view.login.LoginActivity
import com.marzuki.bigerapp.view.register.RegisterActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                binding.topAppBar.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu1 -> {
                            viewModel.logoutWithToken(user.token)
                            viewModel.logout()
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }
}