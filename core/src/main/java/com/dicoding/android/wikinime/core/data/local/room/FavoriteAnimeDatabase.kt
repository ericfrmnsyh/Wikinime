package com.dicoding.android.wikinime.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.android.wikinime.core.data.local.entity.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1, exportSchema = false)
abstract class FavoriteAnimeDatabase : RoomDatabase() {

    abstract fun favoriteAnimeDao(): FavoriteAnimeDao
}