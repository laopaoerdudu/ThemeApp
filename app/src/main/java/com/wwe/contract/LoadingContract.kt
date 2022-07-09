package com.wwe.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface LoadingContract {

    interface LoadingInput {
        fun didClickLoadingSkin()
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