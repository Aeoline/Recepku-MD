package com.aubrey.recepku.data.model.recipe

import android.os.Parcelable
import com.aubrey.recepku.tools.HealthyStepsDeserializer
import com.google.gson.annotations.JsonAdapter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val photoUrl: String,
    val ingredients: List<String>?,
    val steps: List<String>?,
    val healthyIngredients: List<String>?,
    @JsonAdapter(HealthyStepsDeserializer::class)
    val healthySteps: List<String>?,
    val calories: Int,
    val healthyCalories: Int,
) : Parcelable
