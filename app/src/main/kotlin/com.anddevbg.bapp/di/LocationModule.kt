package com.anddevbg.bapp.di

import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides

/**
 * Created by teodorpenkov on 5/22/16.
 */
@Module(includes = arrayOf(AndroidModule::class))
class LocationModule {

    @Provides
    fun provideLocationManager(context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}