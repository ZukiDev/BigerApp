package com.marzuki.bigerapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.data.pref.UserModel
import com.marzuki.bigerapp.databinding.ActivityLoginBinding
import com.marzuki.bigerapp.view.ViewModelFactory
import com.marzuki.bigerapp.view.main.DashboardActivity
import com.marzuki.bigerapp.view.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRegisterNow()

        binding.btnLogin.setOnClickListener {
            validateAndLogin()
        }
    }

    private fun setRegisterNow() {
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        val spannableString = SpannableString(getString(R.string.register_prompt))

        val customColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val colorSpan = ForegroundColorSpan(customColor)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        spannableString.setSpan(
            colorSpan,
            spannableString.indexOf("Register now!"),
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            clickableSpan,
            spannableString.indexOf("Register now!"),
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvRegister.text = spannableString
        tvRegister.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun validateAndLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            binding.tilEmail.error = "Email cannot be empty"
            return
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password cannot be empty"
            return
        } else {
            binding.tilPassword.error = null
        }

        performLogin(email, password)
    }

    private fun performLogin(email: String, password: String) {
        val progressBar = binding.progressBar

        lifecycleScope.launch {
            try {
                progressBar.visibility = View.VISIBLE

                val response = viewModel.login(email, password)
                Log.d("LoginActivity", "Received username: ${response?.userDetail?.username}")
                if (response != null && response.success == true) {
                    val token: String = response.token.toString()
                    viewModel.saveSession(UserModel(email, token))

                    showAlertDialog("Login Successful") {
                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    showAlertDialog("Invalid Email and Password")
                }
            } catch (e: Exception) {
                showAlertDialog("Login Failed")
                e.printStackTrace()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun showAlertDialog(message: String, onPositiveClick: (() -> Unit)? = null) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                onPositiveClick?.invoke()
            }
            .setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.show()
    }
}