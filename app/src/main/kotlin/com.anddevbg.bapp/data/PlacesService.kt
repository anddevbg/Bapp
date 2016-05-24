package com.anddevbg.bapp.data

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by teodorpenkov on 5/23/16.
 */
interface PlacesService {

    @GET("nearbysearch/json")
    fun nearby(@Query("key") key: String, @Query("location") location: String, @Query("radius") radius: Int = 2000,
               @Query("type") type: String = "bar"): Observable<NearbyResponse>

}