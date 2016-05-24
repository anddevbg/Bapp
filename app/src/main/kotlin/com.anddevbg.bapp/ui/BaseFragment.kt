package com.anddevbg.bapp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anddevbg.bapp.BarApplication
import com.anddevbg.bapp.di.ApplicationComponent

/**
 * Created by teodorpenkov on 5/23/16.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectIntoComponent(BarApplication.graph)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = layoutId()
        val result = inflater.inflate(layout, container, false)

        return result
    }

    protected abstract fun layoutId(): Int

    protected open fun onBind(view: View) {
    }

    protected open fun onUnbind() {
    }

    /**
     * Workaround for Dagger 2 that works only with concrete classes. Alternative is to use reflection.
     * @param component
     */
    open fun injectIntoComponent(component: ApplicationComponent) {
        // Usual implementation will look like this.
        // component.inject(this);
    }
}