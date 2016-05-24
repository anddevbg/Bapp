package com.anddevbg.bapp.di

import com.anddevbg.bapp.data.PlacesService
import com.anddevbg.bapp.di.NetworkModule
import com.anddevbg.bapp.domain.place.NearbyResponseDataMapper
import com.anddevbg.bapp.domain.place.PlaceDataProvider
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.stream.JsonReader
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.RxJavaCallAdapterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by teodorpenkov on 5/23/16.
 */
@Module(includes = arrayOf(NetworkModule::class))
class PlacesModule {

    companion object {
        val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
    }

    @Provides
    @Singleton
    @Named("places") fun provideRetroft(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    fun providePlacesService(@Named("places") retrofit: Retrofit): PlacesService {
        return retrofit.create(PlacesService::class.java)
    }

    @Provides
    @Singleton
    fun provideNearbyResponseDataMapper(): NearbyResponseDataMapper = NearbyResponseDataMapper()

    @Provides
    @Singleton
    fun providePlaceDataProvider(service: PlacesService, nearbyResponseDataMapper: NearbyResponseDataMapper): PlaceDataProvider
            = PlaceDataProvider(service, nearbyResponseDataMapper)
}
