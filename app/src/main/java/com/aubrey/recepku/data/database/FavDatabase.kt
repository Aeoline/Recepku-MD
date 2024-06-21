package com.aubrey.recepku.data.database

import androidx.room.Database
import androidx.room.Room
import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.aubrey.recepku.data.common.StringListConverter

@Database(entities = [FavoriteRecipe::class], version = 3, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class FavDatabase : RoomDatabase() {

    abstract fun favDao(): FavDao

    companion object {
        @Volatile
        private var instance: FavDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): FavDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): FavDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                FavDatabase::class.java, "favRecipeDatabase.db"
            )
                .addMigrations(MIGRATION_1_3)
                .build()
        }

        private val MIGRATION_1_3: Migration = object : Migration(1, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Adding the new column to the FavoriteRecipe table
                database.execSQL("ALTER TABLE FavoriteRecipe ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
