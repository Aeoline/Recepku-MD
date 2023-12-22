package com.aubrey.recepku.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String
)

data class Cookie(

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("expires")
	val expires: String,

	@field:SerializedName("httpOnly")
	val httpOnly: Boolean,

	@field:SerializedName("originalMaxAge")
	val originalMaxAge: Int
)

data class Data(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("cookie")
	val cookie: Cookie,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
