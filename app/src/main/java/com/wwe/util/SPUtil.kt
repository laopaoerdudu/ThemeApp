package com.wwe.util

import android.content.Context

fun putTheme(context: Context, key: String, value: String) {
    context.getSharedPreferences("default_theme", Context.MODE_PRIVATE).also {
        it.edit().putString(key, value).apply()
    }
}

fun getTheme(context: Context, key: String) =
    context.getSharedPreferences("default_theme", Context.MODE_PRIVATE).getString(key, "")