package com.anddevbg.bapp.di

import android.content.Context
import com.anddevbg.bapp.BarApplication
import com.squareup.otto.Bus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by teodorpenkov on 5/22/16.
 */
@Module
class AndroidModule(private val application: BarApplication) {

    @Provides
    fun providerContext(): Context {
        return application;
    }

    @Singleton
    @Provides
    fun provideBus(): Bus {
        return Bus();
    }

}