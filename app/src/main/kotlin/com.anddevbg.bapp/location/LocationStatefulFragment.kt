package com.anddevbg.bapp.location

import android.location.Location
import com.anddevbg.bapp.ui.StatefulFragment
import com.squareup.otto.Produce

/**
 * Created by teodorpenkov on 5/22/16.
 */
class LocationStatefulFragment : StatefulFragment() {

    object Producer {

        var result: Location? = null

        @Produce
        fun produceLocation(): Location? {
            return result
        }
    }

    override fun stateProducer(): Any {
        return Producer
    }

    fun setLocation(location: Location) {
        Producer.result = location
    }

    fun getLocation(): Location? {
        return Producer.result
    }
}
