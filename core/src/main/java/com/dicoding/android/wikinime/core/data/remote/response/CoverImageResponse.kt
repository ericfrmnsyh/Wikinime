package com.dicoding.android.wikinime.core.data.remote.response

import androidx.annotation.Keep

@Keep
data class CoverImageResponse(
    val small: String?,
    val large: String?,
    val original: String?,
)
