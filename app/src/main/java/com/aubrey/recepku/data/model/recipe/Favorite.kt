package com.aubrey.recepku.data.model.recipe

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    val recipe: Recipe
) : Parcelable
