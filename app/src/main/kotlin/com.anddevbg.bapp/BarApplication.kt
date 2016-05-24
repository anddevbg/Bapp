package com.anddevbg.bapp

import android.support.multidex.MultiDexApplication
import com.anddevbg.bapp.di.AndroidModule
import com.anddevbg.bapp.di.ApplicationComponent
import com.anddevbg.bapp.di.DaggerApplicationComponent

/**
 * Created by teodorpenkov on 5/22/16.
 */
class BarApplication : MultiDexApplication() {

    companion object {
        lateinit var graph: ApplicationComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerApplicationComponent.builder().androidModule(AndroidModule(this)).build()
    }
}