package com.anddevbg.bapp.places

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.bindView
import com.anddevbg.bapp.R
import com.anddevbg.bapp.di.ApplicationComponent
import com.anddevbg.bapp.ui.BaseFragment
import com.anddevbg.bapp.ui.misc.setItemClickListener
import com.anddevbg.bapp.ui.misc.unsetItemClickListener
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import javax.inject.Inject

/**
 * Created by teodorpenkov on 5/23/16.
 */
class BarFragment : BaseFragment() {

    companion object {
        fun newInstance(): BarFragment = BarFragment()
    }

    @Inject
    lateinit var bus: Bus

    val barsRecyclerView: RecyclerView by bindView(R.id.bars)
    private lateinit var mBarsAdapter: BarAdapter
    override fun layoutId(): Int = R.layout.fragment_bar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBarsAdapter = BarAdapter()
        barsRecyclerView.adapter = mBarsAdapter
        barsRecyclerView.layoutManager = LinearLayoutManager(view.context)
        barsRecyclerView.setItemClickListener {
            position, view ->
            val place = mBarsAdapter.itemAt(position)
            startMapApp(place.longitude, place.latidude)
        }

        bus.register(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        barsRecyclerView.unsetItemClickListener()
        bus.unregister(this)
    }

    @Subscribe
    fun onBarsUpdate(barEvent: BarStatefulFragment.BarEvent) {
        mBarsAdapter.updateItems(barEvent.bars)
    }

    override fun injectIntoComponent(component: ApplicationComponent) {
        component.inject(this)
    }

    private fun startMapApp(longitude: Double, latitude: Double) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:$latitude,$longitude"))
        startActivity(intent)
    }
}