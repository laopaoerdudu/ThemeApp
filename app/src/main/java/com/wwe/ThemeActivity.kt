package com.wwe

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.wwe.util.getTheme
import com.wwe.util.getThemeColor
import com.wwe.util.getThemeColor2
import com.wwe.util.putTheme

/**
 * 当我们换肤设置了 setTheme 以后，需要重新创建 Activity 才会生效，会导致界面状态丢失
 * */
class ThemeActivity : AppCompatActivity() {
    private var num = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!getTheme(this, "theme").isNullOrBlank()) {
            setTheme(R.style.Theme_Style1)
        }
        setContentView(R.layout.activity_theme_layout)

        findViewById<AppCompatButton>(R.id.btnRecreate).setOnClickListener {
            if (getTheme(this, "theme").isNullOrBlank()) {
                putTheme(this, "theme", "change_skin")
            } else {
                putTheme(this, "theme", "")
            }
            recreate()
        }

        val container = findViewById<LinearLayout>(R.id.viewLayout)

        findViewById<AppCompatButton>(R.id.btnAddView).setOnClickListener {
            val textView = TextView(this).apply {
                text = "WWE Heavy weight champion"
                val textColor = if (num % 2 == 0) {
                    getThemeColor(this@ThemeActivity, R.attr.theme_sub_color, Color.BLACK)
                } else {
                    getThemeColor2(
                        this@ThemeActivity,
                        R.attr.theme_sub_color,
                        Color.BLACK
                    )
                }
                setTextColor(textColor)
            }
            container.addView(
                textView, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            num++
        }
    }
}