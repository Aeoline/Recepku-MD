package com.aubrey.recepku.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: DataProfile,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataProfile(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("created_on")
	val createdOn: CreatedOn,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)

data class CreatedOn(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int,

	@field:SerializedName("_seconds")
	val seconds: Int
)
