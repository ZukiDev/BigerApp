package com.marzuki.bigerapp.view.main.ui.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.data.model.AddUserPreferencesRequest
import com.marzuki.bigerapp.databinding.ActivityAddBinding
import com.marzuki.bigerapp.view.ViewModelFactory
import com.marzuki.bigerapp.view.main.DashboardActivity
import com.marzuki.bigerapp.view.main.ui.settings.SettingFragment
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    private val viewModel by viewModels<AddViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.getUserPreferences()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userPreferencesState.collect { preferencesResponse ->
                if (preferencesResponse != null) {
                    if (preferencesResponse.success == true) {
                        val preferences = preferencesResponse.preferences
                        val addressComponents = preferences?.addressComponents
                        val formattedAddress = preferences?.formattedAddress
                        Log.d("AddActivity", "$addressComponents")
                        if (addressComponents != null) {
                            binding.etPreferenceAddress.setText(addressComponents.address ?: "")
                            binding.etPreferenceRoute.setText(addressComponents.route ?: "")
                            binding.etPreferenceAdminLevel5.setText(addressComponents.administrative_area_level_5 ?: "")
                            binding.etPreferenceAdminLevel4.setText(addressComponents.administrative_area_level_4 ?: "")
                            binding.etPreferenceAdminLevel3.setText(addressComponents.administrative_area_level_3 ?: "")
                            binding.etPreferenceAdminLevel2.setText(addressComponents.administrative_area_level_2 ?: "")
                            binding.etPreferenceAdminLevel1.setText(addressComponents.administrative_area_level_1 ?: "")
                            binding.etPreferencePostalCode.setText(addressComponents.postal_code?.toString() ?: "")

                        } else {
                            Log.d("AddActivity", "Address components are null")
                        }
                    } else {
                        Log.d("AddActivity", "GetUserPreferences request not successful. Message: ${preferencesResponse.preferences}")
                    }
                } else {
                    Log.d("AddActivity", "Received null preferencesResponse")
                }
            }
        }

        setupUI()
    }

    private fun setupUI() {
        binding.btnAdd.setOnClickListener {
            binding.btnAdd.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE

            val requestBody = createAddUserPreferencesRequest()

            lifecycleScope.launch {
                viewModel.addUserPreferences(requestBody).let { response ->
                    binding.progressBar.visibility = View.GONE
                    binding.btnAdd.isEnabled = true

                    if (response.isSuccessful) {
                        showToast("Location Updated")
                        navigateToDashboard()
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                        showToast("Error: $errorMessage")
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createAddUserPreferencesRequest(): AddUserPreferencesRequest {
        return AddUserPreferencesRequest(
            address = binding.etPreferenceAddress.text.toString(),
            route = binding.etPreferenceRoute.text.toString(),
            administrative_area_level_5 = binding.etPreferenceAdminLevel5.text.toString(),
            administrative_area_level_4 = binding.etPreferenceAdminLevel4.text.toString(),
            administrative_area_level_3 = binding.etPreferenceAdminLevel3.text.toString(),
            administrative_area_level_2 = binding.etPreferenceAdminLevel2.text.toString(),
            administrative_area_level_1 = binding.etPreferenceAdminLevel1.text.toString(),
            postal_code = binding.etPreferencePostalCode.text.toString().toIntOrNull() ?: 0
        )
    }

    private fun navigateToDashboard() {
        val intent = Intent(this@AddActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}
