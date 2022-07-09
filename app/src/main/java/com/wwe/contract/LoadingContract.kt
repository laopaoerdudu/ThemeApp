package com.wwe.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wwe.framework.SkinInflaterFactory

interface LoadingContract {

    interface LoadingInput {
        fun didClickApplySkin(layoutFactory2: SkinInflaterFactory)
        fun didClickResetSkin(layoutFactory2: SkinInflaterFactory)
    }

    interface LoadingOutput {
        val title: LiveData<String>
        val content: LiveData<String>
        val isFloatingButtonVisibility: MutableLiveData<Int>
    }

    interface LoadingRouter {
        fun navigateToDetail(fragment: Fragment)
    }
}