package com.custom.devunlocker

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 60, 60, 60)
        }

        val title = TextView(this).apply {
            text = "Android TV Developer / USB Debugging Unlocker"
            textSize = 22f
            setPadding(0, 0, 0, 40)
        }
        layout.addView(title)

        addActionButton(layout, "1. เปิด Developer Options (Standard Intent)") {
            tryLaunchIntent(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        }

        addActionButton(layout, "2. บังคับเปิด TV Settings -> DevelopmentActivity") {
            val intent = Intent().apply {
                component = ComponentName(
                    "com.android.tv.settings",
                    "com.android.tv.settings.system.development.DevelopmentActivity"
                )
            }
            tryLaunchIntent(intent)
        }

        addActionButton(layout, "3. เปิดหน้า About (กด Build Number 7 ครั้ง)") {
            tryLaunchIntent(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS))
        }

        addActionButton(layout, "4. เปิด Android Settings พื้นฐาน") {
            tryLaunchIntent(Intent(Settings.ACTION_SETTINGS))
        }

        setContentView(layout)
    }

    private fun addActionButton(parent: LinearLayout, label: String, onClick: () -> Unit) {
        val btn = Button(this).apply {
            text = label
            isFocusable = true
            isFocusableInTouchMode = true
            setOnClickListener { onClick() }
        }
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 0, 0, 24)
        }
        parent.addView(btn, params)
    }

    private fun tryLaunchIntent(intent: Intent) {
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "ไม่สามารถเปิดได้: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
