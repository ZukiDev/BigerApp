package com.marzuki.bigerapp.view.main.ui.community

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.marzuki.bigerapp.data.model.Post
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class CommunityViewModel(private val apiService: ApiServicePost, private val userRepository: UserRepository) : ViewModel() {

    val posts: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { PostPagingSource(apiService, userRepository) }
    ).flow
}