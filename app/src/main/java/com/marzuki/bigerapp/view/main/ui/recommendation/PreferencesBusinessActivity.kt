package com.marzuki.bigerapp.view.main.ui.recommendation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.data.model.BusinessRecommendationRequest
import com.marzuki.bigerapp.data.network.predict.ApiConfigPredict
import com.marzuki.bigerapp.databinding.ActivityPreferencesBusinessBinding
import com.marzuki.bigerapp.di.Injection
import com.marzuki.bigerapp.view.main.DashboardActivity

class PreferencesBusinessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferencesBusinessBinding
    private lateinit var spinnerPrice: Spinner
    private lateinit var spinnerRating: Spinner

    private val viewModel: BusinessViewModel by viewModels {
        BusinessViewModelFactory(
            ApiConfigPredict.getApiService(),
            Injection.provideUserRepository(this),
            Injection.provideDataRepository(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPreferencesBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spinnerPrice = binding.spinnerPrice
        spinnerRating = binding.spinnerRating

        populateSpinner(spinnerPrice, R.array.price_options)
        populateSpinner(spinnerRating, R.array.rating_options)

        binding.btnAdd.setOnClickListener {
            val selectedPrice = spinnerPrice.selectedItem?.toString()
            val selectedRating = spinnerRating.selectedItem?.toString()

            if (selectedPrice != null && selectedRating != null) {
                viewModel.getData().observe(this) { user ->
                    val formatted = user.formattedAddress
                    val request = BusinessRecommendationRequest(formatted, selectedPrice, selectedRating)
                    viewModel.fetchBusinessRecommendations(request)

                    viewModel.businessState.observe(this) { state ->
                        handleBusinessState(state)
                    }
                    navigateToGeneratedBusiness(selectedPrice, selectedRating)
                }
            } else {
                Toast.makeText(this, "Please select both Price and Rating", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleBusinessState(state: BusinessState) {
        when (state) {
            is BusinessState.Success -> {
                Toast.makeText(this, "Here the result", Toast.LENGTH_SHORT).show()
                showLoading(false)
                binding.btnAdd.isEnabled = true
            }
            is BusinessState.Error -> {
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                binding.btnAdd.isEnabled = true
                showLoading(false)
            }
            BusinessState.Loading -> {
                binding.btnAdd.isEnabled = false
                showLoading(true)
            }
        }
    }

    private fun populateSpinner(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(
            this,
            arrayResId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            spinner.adapter = adapter
        }
    }

    private fun navigateToGeneratedBusiness(selectedPrice: String, selectedRating: String) {
        val intent = Intent(this@PreferencesBusinessActivity, GeneratedBusinessActivity::class.java).apply {
            putExtra("SELECTED_PRICE", selectedPrice)
            putExtra("SELECTED_RATING", selectedRating)
        }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}