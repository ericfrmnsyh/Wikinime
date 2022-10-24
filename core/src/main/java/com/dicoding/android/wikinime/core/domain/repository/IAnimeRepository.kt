package com.dicoding.android.wikinime.core.domain.repository

import com.dicoding.android.wikinime.core.domain.model.Anime
import com.dicoding.android.wikinime.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface IAnimeRepository {
    fun getSearchAnime(query: String): Flow<Resource<List<Anime>>>

    fun getDetailAnime(id: String): Flow<Resource<Anime>>

    fun getFavoriteAnime(): Flow<List<Anime>>

    fun isFavoriteAnime(id: String): Int

    suspend fun insertFavoriteAnime(anime: Anime)

    suspend fun deleteFavoriteAnime(anime: Anime): Int
}