package com.yuliakazachok.workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yuliakazachok.workmanager.ui.FileScreen
import com.yuliakazachok.workmanager.ui.theme.WorkManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WorkManagerTheme {
                FileScreen()
            }
        }
    }
}