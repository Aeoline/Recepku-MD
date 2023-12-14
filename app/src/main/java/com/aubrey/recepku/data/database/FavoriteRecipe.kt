package com.aubrey.recepku.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteRecipe(
    @PrimaryKey(autoGenerate = false)

    @ColumnInfo (name = "id")
    var id: Int?,

    @ColumnInfo (name = "title")
    var title: String?,

    @ColumnInfo (name = "description")
    var description: String?,

    @ColumnInfo (name = "photoUrl")
    var photoUrl: String?,

    @ColumnInfo (name = "ingredients")
    var ingredients: List<String?>?,

    @ColumnInfo (name = "steps")
    var steps: List<String?>?,

    @ColumnInfo (name = "healthyIngredients")
    var healthyIngredients: List<String?>?,

    @ColumnInfo (name = "healthySteps")
    var healthySteps: List<String?>?,

    @ColumnInfo (name = "calories")
    var calories: Int?,

    @ColumnInfo (name = "healthyCalories")
    var healthyCalories: Int?,

    ) : Parcelable
