package com.anddevbg.bapp.domain.place

/**
 * Created by teodorpenkov on 5/23/16.
 */
data class Place(val name: String, val longitude: Double, val latidude: Double, val vicinity: String) {
    var distanceTo: Float = 0.0f
}
