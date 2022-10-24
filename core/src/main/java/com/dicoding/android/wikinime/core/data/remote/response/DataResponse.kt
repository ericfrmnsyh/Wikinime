package com.dicoding.android.wikinime.core.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataResponse(

    @field:SerializedName("id")
    val id: String?,

    @field:SerializedName("attributes")
    val attributes: AttributesResponse?,
)
