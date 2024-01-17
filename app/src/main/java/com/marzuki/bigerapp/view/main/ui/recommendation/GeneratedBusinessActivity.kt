package com.marzuki.bigerapp.view.main.ui.recommendation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.data.model.BusinessRecommendationRequest
import com.marzuki.bigerapp.data.network.predict.ApiConfigPredict
import com.marzuki.bigerapp.databinding.ActivityGeneratedBusinessBinding
import com.marzuki.bigerapp.di.Injection
import com.marzuki.bigerapp.view.main.DashboardActivity

class GeneratedBusinessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGeneratedBusinessBinding

    private val viewModel: BusinessViewModel by viewModels {
        BusinessViewModelFactory(
            ApiConfigPredict.getApiService(),
            Injection.provideUserRepository(this),
            Injection.provideDataRepository(this)
        )
    }

    private lateinit var businessAdapter: BusinessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratedBusinessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedPrice = intent.getStringExtra("SELECTED_PRICE") ?: "MODERATE"
        val selectedRating = intent.getStringExtra("SELECTED_RATING") ?: "5.0"

        businessAdapter = BusinessAdapter()

        binding.recyclerViewBusiness.apply {
            layoutManager = LinearLayoutManager(this@GeneratedBusinessActivity)
            adapter = businessAdapter
        }

        viewModel.businessState.observe(this) { state ->
            when (state) {
                is BusinessState.Loading -> {
                    showLoading(true)
                }

                is BusinessState.Success -> {
                    businessAdapter.submitList(state.recommendations)
                    showLoading(false)
                }

                is BusinessState.Error -> {
                    showLoading(false)
                }
            }
        }

        fetchBusinessRecommendations(selectedPrice, selectedRating)
    }

    private fun fetchBusinessRecommendations(selectedPrice: String, selectedRating: String) {
        viewModel.getData().observe(this) { user ->
            val formatted = user.formattedAddress
            val request = BusinessRecommendationRequest(formatted, selectedPrice, selectedRating)
            viewModel.fetchBusinessRecommendations(request)
        }
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