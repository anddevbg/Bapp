package com.anddevbg.bapp.ui

import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import butterknife.bindOptionalView
import com.anddevbg.bapp.BarApplication
import com.anddevbg.bapp.BuildConfig
import com.anddevbg.bapp.R
import com.anddevbg.bapp.di.ApplicationComponent


abstract class BaseActivity : AppCompatActivity() {

    val toolbar: Toolbar? by bindOptionalView(R.id.toolbarActionbar)

    val progressBar: ContentLoadingProgressBar? by bindOptionalView(R.id.progressBar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resId: Int? = layoutId()
        if (resId != null) {
            setContentView(resId)
        }

        if (toolbar != null) {
            setupToolbar(toolbar as Toolbar)
            setSupportActionBar(toolbar)
            onSupportActionBarSet(supportActionBar)
        }

        injectIntoComponent(BarApplication.graph)
    }

    abstract fun layoutId(): Int?

    protected open fun setupToolbar(toolbar: Toolbar) {
    }

    protected open fun onSupportActionBarSet(supportActionBar: ActionBar?) {
    }

    protected open fun injectIntoComponent(graph: ApplicationComponent) {
    }

    protected fun debugLog(message: String, tag: String = this.javaClass.simpleName) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
}
