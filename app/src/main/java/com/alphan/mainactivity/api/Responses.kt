package com.alphan.mainactivity.api

import com.google.gson.annotations.SerializedName

data class NearbyPlacesResponse(
        @SerializedName("success") var success: Int = 0
)