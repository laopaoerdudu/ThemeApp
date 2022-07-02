package com.wwe.util

import android.content.Context
import android.util.TypedValue

/**
 * 直接写死的颜色不处理
 * ?2130903258 ?colorPrimary 这种方式的解析主题先找到 id，再去找资源名称和类型
 * @2131231208 @color/red 直接根据 id 找到资源名称和类型
 * */

fun getThemeColor(context: Context, attr: Int, defaultColor: Int): Int {
    val obtainStyledAttributes = context.theme.obtainStyledAttributes(intArrayOf(attr))
    val redIds = IntArray(obtainStyledAttributes.indexCount)
    for (i in 0 until obtainStyledAttributes.indexCount) {
        val type = obtainStyledAttributes.getType(i)
        redIds[i] =
            if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
                obtainStyledAttributes.getColor(i, defaultColor)
            } else {
                defaultColor
            }
    }
    obtainStyledAttributes.recycle()
    return redIds[0]
}

/**
 * TypedValue 详解
 * 针对 #ffffff 这种，resourceId 为0
 * 针对 @color/black，resourceId 为 R.color.black
 * 针对 @drawable/XXX, resourceId 为 R.drawable.XXX，type 为 TypedValue.TYPE_STRING,string 字段为文件名
 * */
fun getThemeColor2(context: Context, attr: Int, defaultColor: Int): Int {
    val typedValue = TypedValue()
    val success = context.theme.resolveAttribute(
        attr,
        typedValue,
        true
    )
    return if (success) {
        if (typedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT
            && typedValue.type <= TypedValue.TYPE_LAST_COLOR_INT
        ) {
            typedValue.data
        } else {
            defaultColor
        }
    } else {
        defaultColor
    }
}