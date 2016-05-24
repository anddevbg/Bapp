package com.anddevbg.bapp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import com.anddevbg.bapp.BarApplication
import com.squareup.otto.Bus
import javax.inject.Inject

/**
 * Created by teodorpenkov on 5/22/16.
 *
 * Pattern from https://developer.android.com/guide/topics/resources/runtime-changes.html
 */
abstract class StatefulFragment : Fragment() {

    @Inject
    lateinit var bus: Bus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        BarApplication.graph.inject(this);
        bus.register(stateProducer())
    }

    override fun onDestroy() {
        super.onDestroy()

        bus.unregister(stateProducer())
    }

    abstract fun stateProducer(): Any

}
