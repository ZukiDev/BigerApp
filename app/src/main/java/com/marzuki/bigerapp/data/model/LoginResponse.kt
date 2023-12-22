package com.marzuki.bigerapp.data.model

data class LoginResponse(
	val success: Boolean? = null,
	val message: String? = null,
	val token: String? = null,
	val userDetail: UserDetail? = null
)

data class UserDetail(
	val userId: String? = null,
	val username: String? = null,
	val email: String? = null,
	val dateCreated: String? = null,
	val dateUpdated: DateUpdated? = null
)

data class DateUpdated(
	val seconds: Long? = null,
	val nanoseconds: Long? = null
)


