package com.wwe

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wwe.framework.SkinInflaterFactory
import com.wwe.loading.LoadingSkinViewModel
import com.wwe.loading.LoadingSkinViewModelFactory
import java.lang.ref.WeakReference

// Ref: https://github.com/fengjundev/Android-Skin-Loader
class FactoryActivity : AppCompatActivity() {
    private val mViewModel: LoadingSkinViewModel by viewModels {
        LoadingSkinViewModelFactory(
            WeakReference(this), "skin.apk"
        )
    }

    private val tvTitle: TextView by lazy {
        findViewById(R.id.tvTitle)
    }

    private val tvContent: TextView by lazy {
        findViewById(R.id.tvContent)
    }

    private val viewLayout: LinearLayout by lazy {
        findViewById(R.id.viewLayout)
    }

    private val floatingButton: FloatingActionButton by lazy {
        findViewById(R.id.floatingButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutFactory2 = SkinInflaterFactory(delegate)
        LayoutInflaterCompat.setFactory2(layoutInflater, layoutFactory2)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factory_layout)

        findViewById<Button>(R.id.applySkinBtn).setOnClickListener {
            mViewModel.didClickApplySkin(layoutFactory2)
        }

        findViewById<Button>(R.id.resetSkinBtn).setOnClickListener {
            mViewModel.didClickResetSkin(layoutFactory2)
        }

        findViewById<Button>(R.id.addWidgetBtn).setOnClickListener {
            val attrView = layoutFactory2.dynamicAddView(TextView(this).apply {
                setOnClickListener {
                    viewLayout.removeView(it)
                }
            }).apply {
                addAttrItem("text", R.string.add_text_string)
                addAttrItem("textColor", R.color.skin_color)
            }
            layoutFactory2.applyAttrView(this, attrView)
            viewLayout.addView(attrView.view, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ))
        }

        mViewModel.content.observe(this) {
            tvContent.text = it
        }

        mViewModel.title.observe(this) {
            tvTitle.text = it
        }

        mViewModel.isFloatingButtonVisibility.observe(this) {
            floatingButton.visibility = it
        }
    }
}