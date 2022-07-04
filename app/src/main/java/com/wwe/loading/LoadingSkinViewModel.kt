package com.wwe.loading

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.wwe.R
import com.wwe.contract.LoadingContract
import com.wwe.helper.SkinLoader
import com.wwe.util.SingleLiveEvent
import kotlinx.coroutines.*
import java.io.File
import java.lang.ref.WeakReference

class LoadingSkinViewModel(
    val context: WeakReference<Context>,
    val skinName: String
) : ViewModel(), LoadingContract.LoadingInput, LoadingContract.LoadingOutput  {
    val showLoading: SingleLiveEvent by lazy { SingleLiveEvent() }
    val hideLoading: SingleLiveEvent by lazy { SingleLiveEvent() }

    override fun didClickLoadingSkin() {
        viewModelScope.launch {
            loadingSkin()
        }
    }

    suspend fun loadingSkin() {
        (context.get() as? AppCompatActivity)?.let { activity ->
            withContext(Dispatchers.IO) {
                SkinLoader.loadSkinResource(activity, File(activity.getExternalFilesDir(null), "skin.apk").absolutePath)
                Log.i("WWE", "LoadingSkinViewModel -> applySkin()")
            }
        }
    }

    override val content: LiveData<String> by lazy {
        MutableLiveData((context.get() as? Context)?.getString(R.string.test_string))
    }

    override val isFloatingButtonVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)
}

class LoadingSkinViewModelFactory(val context: WeakReference<Context>, val skinName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadingSkinViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoadingSkinViewModel(context, skinName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}