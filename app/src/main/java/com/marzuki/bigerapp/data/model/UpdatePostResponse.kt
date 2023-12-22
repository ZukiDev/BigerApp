package com.marzuki.bigerapp.data.model

data class UpdatePostRequest(
    val title: String,
    val text: String
)

data class UpdatePostResponse(
    val _writeTime: WriteTime
)