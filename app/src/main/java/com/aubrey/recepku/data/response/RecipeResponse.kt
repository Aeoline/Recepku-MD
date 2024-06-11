package com.aubrey.recepku.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("healthySteps")
	val healthySteps: List<String?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("steps")
	val steps: List<String?>? = null,

	@field:SerializedName("healthyCalories")
	val healthyCalories: Int? = null,

	@field:SerializedName("healthyIngredients")
	val healthyIngredients: List<String?>? = null,

	@field:SerializedName("ingredients")
	val ingredients: List<String?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("calories")
	val calories: Int? = null,

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("isFavorite")
	var isFavorite: Boolean

) : Parcelable
