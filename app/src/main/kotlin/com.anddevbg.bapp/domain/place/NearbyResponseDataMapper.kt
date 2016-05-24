package com.anddevbg.bapp.domain.place

import com.anddevbg.bapp.data.NearbyResponse
import com.anddevbg.bapp.domain.ResponseMapper

/**
 * Created by teodorpenkov on 5/23/16.
 */
class NearbyResponseDataMapper : ResponseMapper<NearbyResponse, List<Place>> {

    override fun map(param: NearbyResponse): List<Place> {

        return param.places.map { Place(it.name, it.geometry.location.lng, it.geometry.location.lat, it.vicinity) }
    }


}