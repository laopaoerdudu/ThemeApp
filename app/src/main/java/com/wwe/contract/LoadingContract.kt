package com.wwe.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wwe.framework.SkinInflaterFactory
import com.wwe.util.SingleLiveEvent

interface LoadingContract {

    interface LoadingInput {
        fun viewDidLoad()
        fun didClickCloseFlow()
        fun didClickNavigation()
        fun didClickApplySkin(layoutFactory2: SkinInflaterFactory)
        fun didClickResetSkin(layoutFactory2: SkinInflaterFactory)
    }

    interface LoadingOutput {
        val title: LiveData<String>
        val content: LiveData<String>
        val isFloatingButtonVisibility: MutableLiveData<Int>
        val showLoading: SingleLiveEvent
        val hideLoading: SingleLiveEvent
        val dismissFlow: SingleLiveEvent
        val navigateToMainActivity: SingleLiveEvent
    }
}