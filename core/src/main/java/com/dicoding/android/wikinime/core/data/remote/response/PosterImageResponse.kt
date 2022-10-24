package com.dicoding.android.wikinime.core.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PosterImageResponse(

    @field:SerializedName("medium")
    val medium: String?,

    @field:SerializedName("large")
    val large: String?,

    @field:SerializedName("original")
    val original: String?,
)
