package com.dicoding.android.wikinime.core.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DetailResponse(

    @field:SerializedName("data")
    val data: DataResponse
)
