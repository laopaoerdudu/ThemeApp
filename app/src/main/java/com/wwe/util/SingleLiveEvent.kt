package com.wwe.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent : MutableLiveData<Boolean>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in Boolean>) {
        if (hasActiveObservers()) {
            Log.w(
                TAG,
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        super.observe(owner, { t ->
            // 当 LiveData 进行 setValue 时，AtomicBoolean 的值 设为 false
            // 当 页面重建时，执行 compareAndSet 发现，已经无法更新 mPending 的值了，并不会继续触发 onChanged（T）方法了
            // 即只有在 setValue 时相应一次onChanged(T)方法。
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: Boolean?) {
        mPending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun call() {
        value = null
    }

    override fun postValue(value: Boolean?) {
        mPending.set(true)
        super.postValue(value)
    }

    fun postCall() {
        postValue(null)
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}