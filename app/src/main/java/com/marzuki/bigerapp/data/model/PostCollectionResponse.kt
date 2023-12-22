package com.marzuki.bigerapp.data.model

data class PostCollectionResponse(
    val totalPages: Int,
    val posts: List<Post> = emptyList()
)

data class Post(
    val documentId: String,
    val post: PostDetails
)

data class PostDetails(
    val imageName: String,
    val imageMedia: String,
    val imageUrl: String,
    val publisher: String,
    val postId: String,
    val userReference: String,
    val likes: Int,
    val dateCreated: DateCreated,
    val text: String,
    val title: String,
)

data class DateCreated(
    val _seconds: Long,
    val _nanoseconds: Long
)