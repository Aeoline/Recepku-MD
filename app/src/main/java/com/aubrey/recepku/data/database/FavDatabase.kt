package com.aubrey.recepku.data.database

import androidx.room.Database
import androidx.room.Room
import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aubrey.recepku.data.common.StringListConverter

@Database(entities = [FavoriteRecipe::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class FavDatabase : RoomDatabase() {

    abstract fun favDao(): FavDao

    companion object{
        @Volatile
        private var instance: FavDatabase? = null

        @JvmStatic
        fun getInstance(context:Context): FavDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavDatabase::class.java,
                    "favRecipeDatabase.db"
                ).build()
            }
        }
}