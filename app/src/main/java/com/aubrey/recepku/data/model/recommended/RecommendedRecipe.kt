package com.aubrey.recepku.data.model.recommended

data class RecommendedRecipe(
    val id: Int,
    val title: String,
    val description: String,
    val photoUrl: String,
    val ingredients: List<String>?,
    val steps: List<String>?,
    val healthyIngredients: List<String>?,
    val healthySteps: List<String>?,
    val calories: Int,
    val healthyCalories: Int,
    var isFavorite: Boolean = false
)
