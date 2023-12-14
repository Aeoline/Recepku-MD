package com.aubrey.recepku.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: FavoriteRecipe)

    @Delete
    suspend fun delete(favorite: FavoriteRecipe)

    @Query("SELECT * FROM FavoriteRecipe")
    fun getFav(): LiveData<List<FavoriteRecipe>>

    @Query("SELECT * FROM FavoriteRecipe WHERE id = :id")
    fun isFav(id : Int) : LiveData<FavoriteRecipe>

    @Query("SELECT * FROM FavoriteRecipe WHERE id = :id")
    fun getFavoriteById(id: Int): LiveData<FavoriteRecipe?>
}