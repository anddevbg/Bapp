package com.anddevbg.bapp.data

import com.google.gson.annotations.SerializedName

/**
 * Created by teodorpenkov on 5/23/16.
 */
data class NearbyResponse(@SerializedName("results") val places: List<Place>)

data class Place(val geometry: Geometry, val name: String, val vicinity: String)

data class Geometry(val location: Location)

data class Location(val lat: Double, val lng: Double)