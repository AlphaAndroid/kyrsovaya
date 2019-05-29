package com.alphan.mainactivity.models

data class SearchPlaceModel(
        var mainPlaceName: String,
        var placeInfo: String? = "",
        var lat: Double,
        var lng: Double
)