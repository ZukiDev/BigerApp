package com.marzuki.bigerapp.view.main.ui.community

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marzuki.bigerapp.R
import com.marzuki.bigerapp.data.model.Post
import com.marzuki.bigerapp.databinding.ItemPostBinding

class PostAdapter : PagingDataAdapter<Post, PostAdapter.PostViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        post?.let {
            holder.bind(it)
            Log.d("PostAdapter", "Item bound at position $position: $it")
        }
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.apply {
                tvPublisher.text = post.post.publisher
                tvTitlePost.text = post.post.title
                tvTextPost.text = post.post.text
                Glide.with(itemView.context)
                    .load(post.post.imageUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_repeat).error(R.drawable.ic_broken_image))
                    .into(ivPost)
            }
        }
    }

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.post.postId == newItem.post.postId

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.post == newItem.post
        }
    }
}
