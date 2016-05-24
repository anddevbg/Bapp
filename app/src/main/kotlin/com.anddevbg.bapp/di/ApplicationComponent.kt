package com.anddevbg.bapp.di

import com.anddevbg.bapp.places.BarActivity
import com.anddevbg.bapp.places.BarFragment
import com.anddevbg.bapp.places.MapFragment
import com.anddevbg.bapp.ui.StatefulFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by teodorpenkov on 5/22/16.
 */
@Singleton
@Component(modules = arrayOf(AndroidModule::class, LocationModule::class, PlacesModule::class))
interface ApplicationComponent {

    fun inject(barActivity: BarActivity)

    fun inject(statefulFragment: StatefulFragment)
    fun inject(barFragment: BarFragment)
    fun inject(mapFragment: MapFragment)
}