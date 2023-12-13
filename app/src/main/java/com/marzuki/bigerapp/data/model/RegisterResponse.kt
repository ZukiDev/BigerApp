package com.marzuki.bigerapp.data.model

data class RegisterResponse(
	val store: Store? = null,
	val message: String? = null,
	val error: Boolean? = null
)

data class WriteTime(
	val nanoseconds: Int? = null,
	val seconds: Int? = null
)

data class Store(
	val writeTime: WriteTime? = null
)

