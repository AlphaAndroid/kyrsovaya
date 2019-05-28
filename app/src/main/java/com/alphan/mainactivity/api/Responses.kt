package com.alphan.mainactivity.api

import com.google.gson.annotations.SerializedName

data class NearbyPlacesResponse(
        @SerializedName("status") var status: String,
        @SerializedName("results") var results: List<Place>
) {
    inner class Place(
            @SerializedName("geometry") var geometry: Geometry,
            @SerializedName("name") var name: String,
            @SerializedName("opening_hours") var openingStatus: OpeningStatus?,
            @SerializedName("rating") var rating: Double,
            @SerializedName("vicinity") var address: String
    ) {
        inner class Geometry(
                @SerializedName("location") var location: Location
        ) {
            inner class Location(
                    @SerializedName("lat") var lat: Double,
                    @SerializedName("lng") var lng: Double
            )
        }

        inner class OpeningStatus(@SerializedName("open_now") var isOpened: Boolean)
    }
}