package com.marzuki.bigerapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.marzuki.bigerapp.data.model.Post
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.view.main.ui.community.PostPagingSource

class PostRepository(private val apiService: ApiServicePost, private val userRepository: UserRepository) {

    fun getPosts(): LiveData<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                PostPagingSource(apiService, userRepository)
            }
        ).liveData
    }
}
