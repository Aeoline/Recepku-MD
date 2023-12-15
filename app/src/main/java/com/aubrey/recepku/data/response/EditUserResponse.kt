package com.aubrey.recepku.data.response

import com.google.gson.annotations.SerializedName

data class EditUserResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
