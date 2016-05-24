package com.anddevbg.bapp.places

import com.anddevbg.bapp.domain.place.Place
import com.anddevbg.bapp.ui.StatefulFragment
import com.squareup.otto.Produce

/**
 * Created by teodorpenkov on 5/23/16.
 */
class BarStatefulFragment : StatefulFragment() {

    object Producer {
        var result: List<Place>? = null

        @Produce
        fun produceBarEvent(): BarEvent? {
            if (result != null) {
                return BarEvent(result as List<Place>)
            }

            return null
        }

    }

    data class BarEvent(val bars: List<Place>)

    override fun stateProducer(): Any {
        return Producer
    }

    fun setBars(bars: List<Place>) {
        Producer.result = bars
    }

    fun getBars(): List<Place>? {
        return Producer.result
    }
}