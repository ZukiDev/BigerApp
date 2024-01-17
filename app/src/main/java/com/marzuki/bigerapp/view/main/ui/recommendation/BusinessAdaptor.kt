package com.marzuki.bigerapp.view.main.ui.recommendation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.marzuki.bigerapp.data.model.BusinessRecommendationResponse
import com.marzuki.bigerapp.databinding.ItemBusinessRecommendationBinding

class BusinessAdapter :
    ListAdapter<BusinessRecommendationResponse, BusinessAdapter.BusinessViewHolder>(BusinessDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBusinessRecommendationBinding.inflate(inflater, parent, false)
        return BusinessViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val business = getItem(position)
        holder.bind(business)
    }

    class BusinessViewHolder(private val binding: ItemBusinessRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(business: BusinessRecommendationResponse) {
            Log.d("BusinessAdapter", "Restaurant Name: ${business.Restaurant}")
            binding.tvRestaurantName.text = business.Restaurant
            binding.tvPrice.text = business.price
            binding.tvRating.text = business.rating.toString()
            binding.tvCategory.text = business.kategori
        }
    }

    private class BusinessDiffCallback : DiffUtil.ItemCallback<BusinessRecommendationResponse>() {
        override fun areItemsTheSame(
            oldItem: BusinessRecommendationResponse,
            newItem: BusinessRecommendationResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BusinessRecommendationResponse,
            newItem: BusinessRecommendationResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
}