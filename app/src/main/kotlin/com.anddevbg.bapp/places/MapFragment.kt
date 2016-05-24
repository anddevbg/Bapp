package com.anddevbg.bapp.places

import android.location.Location
import android.os.Bundle
import android.view.View
import com.anddevbg.bapp.BarApplication
import com.anddevbg.bapp.domain.place.Place
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import javax.inject.Inject

/**
 * Created by teodorpenkov on 5/23/16.
 */
class MapFragment : SupportMapFragment() {

    @Inject
    lateinit var bus: Bus

    companion object {
        fun newInstance(): MapFragment = MapFragment()
    }

    private var mBars: List<Place> = emptyList()
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BarApplication.graph.inject(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bus.register(this)

        getMapAsync {
            mMap = it
            // TODO [tpenkov] move camere to user's position
            updateMarkers()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        bus.unregister(this)
    }


    @Subscribe
    fun onBarsUpdate(barEvent: BarStatefulFragment.BarEvent) {
        mBars = barEvent.bars
        if (mMap != null) {
            updateMarkers()
        }
    }

    fun updateMarkers() {
        with(mMap!!) {
            mBars.forEach {
                val place = LatLng(it.latidude, it.longitude)
                addMarker(MarkerOptions().position(place).title(it.name))
            }

        }
    }
}