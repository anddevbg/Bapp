package com.anddevbg.bapp.ui.misc

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by teodorpenkov on 5/23/16.
 */

fun RecyclerView.setItemClickListener(listener: (Int, View) -> Unit) {
    AttachListener.listener = listener
    AttachListener.recyclerView = this
    addOnChildAttachStateChangeListener(AttachListener)
}

fun RecyclerView.unsetItemClickListener() {
    removeOnChildAttachStateChangeListener(AttachListener)
}

object AttachListener : RecyclerView.OnChildAttachStateChangeListener {

    lateinit var listener: (Int, View) -> Unit
    lateinit var recyclerView: RecyclerView

    override fun onChildViewAttachedToWindow(view: View?) {
        view?.setOnClickListener { listener.invoke(recyclerView.getChildAdapterPosition(view), view) }
    }

    override fun onChildViewDetachedFromWindow(view: View?) {
        view?.setOnClickListener(null)
    }

}