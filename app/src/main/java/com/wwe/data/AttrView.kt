package com.wwe.data

import android.view.View

class AttrView(val view: View, val attrItems: MutableList<AttrItem> = mutableListOf()) {
    fun addAttrItem(attrName: String, resId: Int): AttrView {
        attrItems.add(AttrItem(attrName, resId))
        return this
    }
}