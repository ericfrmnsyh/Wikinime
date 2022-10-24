package com.dicoding.android.wikinime.core.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AttributesResponse(

    @field:SerializedName("synopsis")
    val synopsis: String?,

    @field:SerializedName("titles")
    val titles: TitlesResponse?,

    @field:SerializedName("canonicalTitle")
    val canonicalTitle: String?,

    @field:SerializedName("averageRating")
    val averageRating: String?,

    @field:SerializedName("userCount")
    val userCount: Int?,

    @field:SerializedName("favoritesCount")
    val favoritesCount: Int?,

    @field:SerializedName("popularityRank")
    val popularityRank: Int?,

    @field:SerializedName("ratingRank")
    val ratingRank: Int?,

    @field:SerializedName("status")
    val status: String?,

    @field:SerializedName("posterImage")
    val posterImage: PosterImageResponse?,

    @field:SerializedName("coverImage")
    val coverImage: CoverImageResponse?,

    @field:SerializedName("episodeCount")
    val episodeCount: Int?,

    @field:SerializedName("youtubeVideoId")
    val youtubeVideoId: String?,

    @field:SerializedName("showType")
    val showType: String?,
)
