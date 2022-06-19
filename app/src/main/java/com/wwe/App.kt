package com.wwe

import android.app.Application
import com.google.android.material.color.DynamicColors

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // 通过系统 Token 将从壁纸提取的颜色方案和 App Theme 关联起来
        // 整个 App 使用动态颜色
        DynamicColors.applyToActivitiesIfAvailable(this)

        // 整个 App 使用自定义颜色
       // DynamicColors.applyToActivitiesIfAvailable(this, R.style.AppTheme_Overlay)
    }
}