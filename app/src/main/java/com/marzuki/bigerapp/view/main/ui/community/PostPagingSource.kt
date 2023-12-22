package com.marzuki.bigerapp.view.main.ui.community

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.marzuki.bigerapp.data.model.Post
import com.marzuki.bigerapp.data.network.post.ApiServicePost
import com.marzuki.bigerapp.data.repository.UserRepository
import retrofit2.HttpException
import java.io.IOException

class PostPagingSource(private val api: ApiServicePost, private val userRepository: UserRepository) : PagingSource<Int, Post>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val page = params.key ?: INITIAL_PAGE_INDEX
        val token = userRepository.getUserToken()

        return try {
            val response = api.getPosts("Bearer $token", page)
            val posts = response.posts

            LoadResult.Page(
                data = posts,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
