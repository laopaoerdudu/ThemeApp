package com.wwe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.wwe.util.getTheme
import com.wwe.util.putTheme

class ThemeActivity : AppCompatActivity() {

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
    }
}