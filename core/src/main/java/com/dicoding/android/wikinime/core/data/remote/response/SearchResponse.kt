package com.dicoding.android.wikinime.core.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchResponse(
    @SerializedName("data")
    val data: ArrayList<DataResponse>
)