package com.marzuki.bigerapp.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.databinding.ActivityRegisterBinding
import com.marzuki.bigerapp.view.login.LoginActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLoginHere()

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun validateAndRegister() {
        val username = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlertDialog("Please fill in all fields.")
            return
        }

        performRegistration(username, email, password)
    }

    private fun performRegistration(username: String, email: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            viewModel.register(username, email, password).collect { response ->
                binding.progressBar.visibility = View.GONE

                if (response.error == false) {
                    showAlertDialog("Registration Successful, Please login") {
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    showAlertDialog("Registration Failed, " + response.message)
                }
            }
        }
    }

    private fun setLoginHere() {
        val tvLogin = binding.tvLogin

        val spannableString = SpannableString(getString(R.string.login_prompt))

        val customColor = ContextCompat.getColor(this, R.color.colorPrimary)

        val colorSpan = ForegroundColorSpan(customColor)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }

        spannableString.setSpan(
            colorSpan,
            spannableString.indexOf("Login here!"),
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            clickableSpan,
            spannableString.indexOf("Login here!"),
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvLogin.text = spannableString
        tvLogin.movementMethod = LinkMovementMethod.getInstance()
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
