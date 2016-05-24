package com.anddevbg.bapp.places

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.widget.Toast
import butterknife.bindView
import com.anddevbg.bapp.R
import com.anddevbg.bapp.di.ApplicationComponent
import com.anddevbg.bapp.domain.place.Place
import com.anddevbg.bapp.domain.place.PlaceDataProvider
import com.anddevbg.bapp.location.*
import com.anddevbg.bapp.ui.BaseActivity
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe
import com.vistrav.ask.Ask
import com.vistrav.ask.annotations.AskGranted
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BarActivity : BaseActivity() {

    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var placeDataProvider: PlaceDataProvider
    @Inject
    lateinit var bus: Bus

    // Views
    val viewPager: ViewPager by bindView(R.id.performanceViewPager)
    val tabs: TabLayout by bindView(R.id.tabs)
    private lateinit var mTabAdapter: BarTabAdapter

    private val mLocationStateFragment: LocationStatefulFragment by lazy {
        var result = supportFragmentManager.findFragmentByTag("location")
        if (result == null) {
            result = LocationStatefulFragment()
            supportFragmentManager.beginTransaction()
                    .add(result, "location").commit()
        }
        result as LocationStatefulFragment
    }

    private val mBarsStateFragment: BarStatefulFragment by lazy {
        var result = supportFragmentManager.findFragmentByTag("bars")
        if (result == null) {
            result = BarStatefulFragment()
            supportFragmentManager.beginTransaction()
                    .add(result, "bars").commit()
        }
        result as BarStatefulFragment
    }

    private lateinit var mSubscriptions: CompositeSubscription;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSubscriptions = CompositeSubscription()
        bus.register(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Ask.on(this)
                    .forPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                    .go();
        } else {
            onLocationGranted()
        }

        bindViewPager()
    }

    private fun bindViewPager() {
        mTabAdapter = BarTabAdapter(supportFragmentManager)
        viewPager.adapter = mTabAdapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun onDestroy() {
        super.onDestroy()

        mSubscriptions.unsubscribe();
        bus.unregister(this)
    }

    override fun layoutId(): Int {
        return R.layout.activity_bar
    }

    override fun injectIntoComponent(graph: ApplicationComponent) {
        super.injectIntoComponent(graph)

        graph.inject(this)
    }

    // FIXME [tpenkov] only happy flow?
    @AskGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationGranted() {
        if (mLocationStateFragment.getLocation() != null && mBarsStateFragment.getBars() != null) {
            return
        }

        val networkSpec = LocationSpec(LocationManager.NETWORK_PROVIDER)
        val gpsSpec = LocationSpec(LocationManager.GPS_PROVIDER)
        val networkObservable = locationManager.locationObservable(networkSpec)
        val gpsObservable = locationManager.locationObservable(gpsSpec)
        val lastKnownObservable = locationManager.lastKnownLocationObservable(LocationManager.NETWORK_PROVIDER)

        progressBar!!.show()

        mSubscriptions.add(Observable.merge(networkObservable, gpsObservable)
                .first()
                .timeout(10000, TimeUnit.MILLISECONDS, lastKnownObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Location?> {

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        when (e) {
                            is LocationErrorReason -> handleLocationErrorReason(e)
                            else -> e?.printStackTrace()
                        }
                    }

                    override fun onNext(t: Location?) {
                        if (t != null) {
                            onLocation(t)
                            mLocationStateFragment.setLocation(t)
                        }
                    }
                }))
    }

    @Subscribe
    fun onLocation(location: Location) {
        Toast.makeText(this, "Got location from provider: ${location.provider}", Toast.LENGTH_SHORT).show()

        placeDataProvider.nearbyBars(location.longitude, location.latitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<Place>> {
                    override fun onCompleted() {
                    }

                    override fun onNext(t: List<Place>) {
                        mBarsStateFragment.setBars(t)
                    }

                    override fun onError(e: Throwable?) {
                        e?.printStackTrace()
                    }
                })
    }

    @Subscribe
    fun onBars(barEvent: BarStatefulFragment.BarEvent) {
        progressBar!!.hide()
    }

    private fun handleLocationErrorReason(e: LocationErrorReason) {
        when (e.reason) {
            LocationErrorReason.PROVIDER_DISABLED -> debugLog("handling provider disabled")
            LocationErrorReason.PROVIDER_DOES_NOT_EXIST -> debugLog("handling provider ${e.locationSpec.provider} does not exist")
        }
    }
}
