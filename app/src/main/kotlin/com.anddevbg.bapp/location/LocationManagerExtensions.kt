package com.anddevbg.bapp.location

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import rx.Observable
import rx.android.MainThreadSubscription

/**
 * Created by teodorpenkov on 5/22/16.
 */

class LocationSpec(val provider: String, val minTime: Long = 50, val minDistance: Float = 2000.0f)

class LocationErrorReason(val reason: Int, val locationSpec: LocationSpec) : Throwable() {

    companion object {
        const val PROVIDER_DISABLED = 0;
        const val PROVIDER_DOES_NOT_EXIST = 1;
    }
}

inline fun LocationManager.lastKnownLocationObservable(provider: String): Observable<Location> {
    return Observable.create<Location> {
        if (!allProviders.contains(provider)) {
            it.onError(LocationErrorReason(LocationErrorReason.PROVIDER_DOES_NOT_EXIST, LocationSpec(provider)))
        } else {
            val location = getLastKnownLocation(provider)
            it.onNext(location)
            it.onCompleted()
        }
    }
}

inline fun LocationManager.locationObservable(spec: LocationSpec): Observable<Location> = Observable.create {
    if (!allProviders.contains(spec.provider)) {
        it.onError(LocationErrorReason(LocationErrorReason.PROVIDER_DOES_NOT_EXIST, spec))
    } else {
        val locationListener = object : LocationListener {
            override fun onProviderDisabled(provider: String?) {
                it.onError(LocationErrorReason(LocationErrorReason.PROVIDER_DISABLED, spec))
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onLocationChanged(location: Location?) {
                it.onNext(location)
            }
        }
        requestLocationUpdates(spec.provider, spec.minTime, spec.minDistance, locationListener)

        it.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                removeUpdates(locationListener)
            }
        })
    }
}