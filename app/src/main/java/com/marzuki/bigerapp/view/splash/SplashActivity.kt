package com.marzuki.bigerapp.view.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.view.main.DashboardActivity
import com.marzuki.bigerapp.view.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val isFirstLaunch = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            .getBoolean("isFirstLaunch", true)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isFirstLaunch) {
                startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
            }
            finish()
        }, 3000)
    }
}