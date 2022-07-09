package com.wwe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.LayoutInflaterCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wwe.framework.SkinInflaterFactory
import com.wwe.loading.LoadingSkinViewModel
import com.wwe.loading.LoadingSkinViewModelFactory
import java.lang.ref.WeakReference

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

    private val floatingButton: FloatingActionButton by lazy {
        findViewById(R.id.floatingButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutFactory2 = SkinInflaterFactory(delegate)
        LayoutInflaterCompat.setFactory2(layoutInflater, layoutFactory2)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factory_layout)
        findViewById<Button>(R.id.applySkinBtn).setOnClickListener {
            mViewModel.didClickLoadingSkin()
            layoutFactory2.applySkin(this)
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