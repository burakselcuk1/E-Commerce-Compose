package com.example.e_commerce_compose.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.e_commerce_compose.R
import com.example.e_commerce_compose.ui.theme.ECommerceComposeTheme

class CrashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            startActivity(Intent(this, CrashActivity::class.java))
            finish()
        }
    }
}