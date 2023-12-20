package com.aubrey.recepku.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class Data(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("cookie")
	val cookie: Cookie? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class Cookie(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("expires")
	val expires: String? = null,

	@field:SerializedName("httpOnly")
	val httpOnly: Boolean? = null,

	@field:SerializedName("originalMaxAge")
	val originalMaxAge: Int? = null
)
