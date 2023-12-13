package com.aubrey.recepku.data.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("healthySteps")
	val healthySteps: String? = null,

	@field:SerializedName("healthyCalories")
	val healthyCalories: Int? = null,

	@field:SerializedName("healthyIngredients")
	val healthyIngredients: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("steps")
	val steps: String? = null,

	@field:SerializedName("isFavorite")
	val isFavorite: Boolean? = null
)
