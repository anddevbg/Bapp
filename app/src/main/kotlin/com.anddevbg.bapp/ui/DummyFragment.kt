package com.anddevbg.bapp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by teodorpenkov on 5/23/16.
 */
class DummyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = View(activity)
        view.setBackgroundColor(arguments.getInt("color"))
        return view
    }

    companion object {

        fun newInstance(color: Int): DummyFragment {
            val args = Bundle()
            args.putInt("color", color)
            val fragment = DummyFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
