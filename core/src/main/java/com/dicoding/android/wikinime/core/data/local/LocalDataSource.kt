package com.dicoding.android.wikinime.core.data.local

import com.dicoding.android.wikinime.core.data.local.entity.AnimeEntity
import com.dicoding.android.wikinime.core.data.local.room.FavoriteAnimeDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val favoriteAnimeDao: FavoriteAnimeDao) {

    fun getFavoriteAnime(): Flow<List<AnimeEntity>> =
        favoriteAnimeDao.getFavoriteAnime()

    fun isFavoriteAnime(id: String): Int = favoriteAnimeDao.isFavoriteAnime(id)

    suspend fun insertFavoriteAnime(anime: AnimeEntity) =
        favoriteAnimeDao.insertFavoriteAnime(anime)

    suspend fun deleteFavoriteAnime(anime: AnimeEntity): Int =
        favoriteAnimeDao.deleteFavoriteAnime(anime)
}