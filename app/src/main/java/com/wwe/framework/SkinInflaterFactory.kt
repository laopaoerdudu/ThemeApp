package com.wwe.framework

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View

// LayoutInflaterFactory marked as Deprecated
class SkinInflaterFactory :  LayoutInflater.Factory2 {

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        TODO("Not yet implemented")
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        // 如果在你在重写的方法中返回 null 的话，就会以系统默认的方式去创建 View
        TODO("Not yet implemented")
    }
}