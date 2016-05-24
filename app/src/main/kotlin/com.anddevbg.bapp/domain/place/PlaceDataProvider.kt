package com.anddevbg.bapp.domain.place

import android.location.Location
import com.anddevbg.bapp.BuildConfig
import com.anddevbg.bapp.data.PlacesService
import rx.Observable

/**
 * Created by teodorpenkov on 5/23/16.
 */
class PlaceDataProvider(val service: PlacesService, val nearbyResponseDataMapper: NearbyResponseDataMapper) {

    fun nearbyBars(longitude: Double, latitude: Double): Observable<List<Place>> {
        val requestLocation = Location("current_location")
        requestLocation.longitude = longitude
        requestLocation.latitude = latitude

        return service.nearby(BuildConfig.GOOGLE_PLACES_API_KEY, "${latitude},${longitude}")
                .map { nearbyResponseDataMapper.map(it) }
                .map {
                    it.map {
                        val placeLocation = Location("place_location")
                        placeLocation.longitude = it.longitude
                        placeLocation.latitude = it.latidude
                        it.distanceTo = distanceBetweenLocations(requestLocation, placeLocation)
                        it
                    }
                }
    }

    fun distanceBetweenLocations(a: Location, b: Location) = a.distanceTo(b)
}