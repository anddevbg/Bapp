package com.anddevbg.bapp.places

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import java.security.InvalidParameterException

/**
 * Created by teodorpenkov on 5/23/16.
 */
class BarTabAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        const val POSITION_BAR = 0;
        const val POSITION_MAP = 1;
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        when (position) {
            POSITION_BAR -> return BarFragment.newInstance()
            POSITION_MAP -> return MapFragment.newInstance()
//            POSITION_BAR -> return DummyFragment.newInstance(Color.BLACK)
//            POSITION_MAP -> return DummyFragment.newInstance(Color.RED)
            else -> throw InvalidParameterException("Expected position 0 or 1")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            POSITION_BAR -> return "Nearby bars"
            POSITION_MAP -> return "Map"
        }
        return "";
    }

}