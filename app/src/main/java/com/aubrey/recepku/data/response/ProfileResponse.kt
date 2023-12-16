package com.aubrey.recepku.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)