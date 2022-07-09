package com.wwe.loading

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.wwe.R
import com.wwe.contract.LoadingContract
import com.wwe.framework.SkinInflaterFactory
import com.wwe.helper.SkinLoader
import com.wwe.util.SingleLiveEvent
import kotlinx.coroutines.*
import java.io.File
import java.lang.ref.WeakReference

class LoadingSkinViewModel(
    private val context: WeakReference<Context>,
    private val skinName: String
) : ViewModel(), LoadingContract.LoadingInput, LoadingContract.LoadingOutput {

    // TODO: update it
    val showLoading: SingleLiveEvent by lazy { SingleLiveEvent() }
    val hideLoading: SingleLiveEvent by lazy { SingleLiveEvent() }

    override fun didClickApplySkin(layoutFactory2: SkinInflaterFactory) {
        viewModelScope.launch {
            loadingSkin()
            (context.get() as? Context)?.let {
                layoutFactory2.applySkin(it)
            }
        }
    }

    override fun didClickResetSkin(layoutFactory2: SkinInflaterFactory) {
        SkinLoader.resetSkin()
        (context.get() as? Context)?.let {
            layoutFactory2.applySkin(it)
        }
    }

    override val content: LiveData<String> by lazy {
        MutableLiveData((context.get() as? Context)?.getString(R.string.content_string))
    }

    override val title: LiveData<String> by lazy {
        MutableLiveData((context.get() as? Context)?.getString(R.string.title_string))
    }

    override val isFloatingButtonVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    private suspend fun loadingSkin() {
        (context.get() as? AppCompatActivity)?.let { activity ->
            withContext(Dispatchers.IO) {
                SkinLoader.loadSkinResource(
                    activity,
                    File(activity.getExternalFilesDir(null), skinName).absolutePath
                )
            }
        }
    }
}

class LoadingSkinViewModelFactory(val context: WeakReference<Context>, val skinName: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadingSkinViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoadingSkinViewModel(context, skinName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}