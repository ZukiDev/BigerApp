package com.marzuki.bigerapp.data.model

data class LoginResponse(
	val success: Boolean? = null,
	val userDetail: UserDetail? = null,
	val message: String? = null,
	val token: String? = null
)

data class DateUpdated(
	val nanoseconds: Int? = null,
	val seconds: Int? = null
)

data class UserDetail(
	val dateCreated: String? = null,
	val userId: String? = null,
	val email: String? = null,
	val username: String? = null,
	val dateUpdated: DateUpdated? = null
)

