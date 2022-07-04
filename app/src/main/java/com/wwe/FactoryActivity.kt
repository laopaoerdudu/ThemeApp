package com.wwe

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.LayoutInflaterCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wwe.framework.SkinInflaterFactory
import com.wwe.loading.LoadingSkinViewModel
import com.wwe.loading.LoadingSkinViewModelFactory
import java.lang.ref.WeakReference

class FactoryActivity : AppCompatActivity() {
    private val layoutFactory2 = SkinInflaterFactory()
    private val mViewModel: LoadingSkinViewModel by viewModels {
        LoadingSkinViewModelFactory(
            WeakReference(this), "skin.apk"
        )
    }

    private val tvTitle: TextView by lazy {
        findViewById(R.id.tvTitle)
    }

    private val floatingButton: FloatingActionButton by lazy {
        findViewById(R.id.floatingButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(layoutInflater, layoutFactory2)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factory_layout)
        findViewById<AppCompatButton>(R.id.applySkinBtn).setOnClickListener {
            mViewModel.didClickLoadingSkin()
            Log.i("WWE", "FactoryActivity -> L39")
            layoutFactory2.applySkin(this)
        }

        mViewModel.content.observe(this) {
            tvTitle.text = it
        }

        mViewModel.isFloatingButtonVisibility.observe(this) {
            floatingButton.visibility = it
        }
    }
}