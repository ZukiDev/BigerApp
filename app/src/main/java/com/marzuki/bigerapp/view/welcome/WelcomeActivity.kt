package com.marzuki.bigerapp.view.welcome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.view.main.DashboardActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val getStartedButton: Button = findViewById(R.id.getStartedButton)
        getStartedButton.setOnClickListener {
            // Save the flag to indicate that the app has been launched
            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("isFirstLaunch", false)
                .apply()

            // Navigate to the DashboardActivity when the "Get Started" button is clicked
            startActivity(Intent(this@WelcomeActivity, DashboardActivity::class.java))
            finish()
        }
    }
}