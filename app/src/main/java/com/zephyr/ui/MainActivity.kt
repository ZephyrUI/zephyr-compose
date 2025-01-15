package com.zephyr.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zephyr.foundations.core.theme.ZephyrTheme
import com.zephyr.sample.SimpleRadioButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            ZephyrTheme {

                SimpleRadioButton()
            }
        }
    }
}