package com.wwe.loading

import android.view.View
import java.util.concurrent.atomic.AtomicInteger

class LoadingManager {
    private val loadingCount: AtomicInteger = AtomicInteger(0)
    private var loadingView: View? = null

    fun showLoading() {
        if(loadingCount.incrementAndGet() > 0) {
            loadingView?.visibility = View.VISIBLE
        }
    }

    fun hideLoading() {
        if(loadingCount.decrementAndGet() <= 0) {
            loadingView?.visibility = View.GONE
            loadingCount.set(0)
        }
    }

    fun attachLoadingView(view: View?) {
        loadingView = view
    }

    fun detachView() {
        loadingView = null
    }
}