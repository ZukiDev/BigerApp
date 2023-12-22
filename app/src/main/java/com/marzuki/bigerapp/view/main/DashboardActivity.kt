package com.marzuki.bigerapp.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.databinding.ActivityDashboardBinding
import com.marzuki.bigerapp.view.ViewModelFactory
import com.marzuki.bigerapp.view.register.RegisterActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val viewModel by viewModels<DashboardViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            } else {
                val navView: BottomNavigationView = binding.navView
                val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
                Log.d("Navigation", "Fragment container ID: ${R.id.nav_host_fragment_activity_dashboard}")
                Log.d("Navigation", "Nav Controller: $navController")

                navView.setupWithNavController(navController)
            }
        }
    }
}