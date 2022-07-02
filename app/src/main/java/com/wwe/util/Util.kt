package com.wwe.util

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi

class Util {

    @RequiresApi(Build.VERSION_CODES.M)
    fun getColorPrimaryDark(mResources: Resources, skinPackageName: String): Int {
        val identify = mResources.getIdentifier("colorPrimaryDark", "color", skinPackageName)
        return mResources.getColor(identify, null)
    }
}