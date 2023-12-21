package com.aubrey.recepku.data.response

data class LoginResponse(
	val data: Data,
	val error: Boolean,
	val message: String,
	val token: String
)

data class Cookie(
	val path: String,
	val expires: String,
	val httpOnly: Boolean,
	val originalMaxAge: Int
)

data class Data(
	val uid: String,
	val cookie: Cookie,
	val imageUrl: String,
	val email: String,
	val username: String
)

