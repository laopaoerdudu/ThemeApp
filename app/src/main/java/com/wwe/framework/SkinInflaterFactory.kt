package com.wwe.framework

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import com.wwe.R
import com.wwe.data.AttrItem
import com.wwe.data.AttrView
import com.wwe.helper.SkinLoader

/**
 * ! 直接写死的颜色不处理
 * ?colorPrimary 这样的先解析主题，然后找到 id，再去找资源名称和类型
 * @color/red 这样的直接根据 id 找到资源名称和类型
 */
// LayoutInflaterFactory marked as Deprecated
class SkinInflaterFactory(private val delegate: AppCompatDelegate) : LayoutInflater.Factory2 {
    private val attrViews: MutableList<AttrView> = mutableListOf()

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.SkinSupport)
        val enableSkin = attributes.getBoolean(R.styleable.SkinSupport_enableSkin, false)
        attributes.recycle()
        if (enableSkin) {
            // 委托给系统的实现，保证兼容性
            val view = delegate.createView(parent, name, context, attrs)
            val attrView = AttrView(view)
            for (i in 0 until attrs.attributeCount) {
                val attributeName = attrs.getAttributeName(i)
                if (isSupportAttr(attributeName)) {
                    val attributeValue = attrs.getAttributeValue(i)
                    val attrId = attributeValue.substring(1)
                    if (attributeValue.startsWith("?")) {
                        // 先解析主题，然后找到 id，再去找资源名称和类型
                        val resIdFromTheme = getResIdFromTheme(context, attrId.toInt())
                        if (resIdFromTheme > 0) {
                            attrView.attrItems.add(AttrItem(attributeName, resIdFromTheme))
                        }
                    } else if (attributeValue.startsWith("@")) {
                        attrView.attrItems.add(AttrItem(attributeName, attrId.toInt()))
                    } else {
                        // No need to handle hard code attributeValue
                    }
                }
            }
            attrViews.add(attrView)
            return view
        }
        return null
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        // 如果在你在重写的方法中返回 null 的话，就会以系统默认的方式去创建 View
        return null
    }

    fun applyAttrView(context: Context, attrView: AttrView) {
        attrView.attrItems.forEach { attrItem ->
            if (attrView.view is TextView) {
                when (attrItem.attrName) {
                    "textColor" -> attrView.view.setTextColor(
                        SkinLoader.getTextColor(
                            context,
                            attrItem.resId
                        )
                    )
                    "text" -> attrView.view.text = SkinLoader.getText(context, attrItem.resId)
                    else -> {
                        /** No need to handle */
                    }
                }
            }
        }
    }

    fun applySkin(context: Context) {
        attrViews.forEach { attrView ->
            if (ViewCompat.isAttachedToWindow(attrView.view)) {
                applyAttrView(context, attrView)
            }
        }
    }

    fun dynamicAddView(view: View): AttrView {
        val attrView = AttrView(view)
        attrViews.add(attrView)
        return attrView
    }

    /**
     * 解析主题，找到资源id
     */
    private fun getResIdFromTheme(context: Context, attrId: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrId, typedValue, true)
        // typedValue.resourceId 可能为0
        return typedValue.resourceId
    }

    private fun isSupportAttr(attrName: String): Boolean {
        if ("textColor" == attrName || "text" == attrName) {
            return true
        }
        return false
    }
}