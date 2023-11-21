package com.yuliakazachok.workmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.yuliakazachok.workmanager.ui.FileScreen
import com.yuliakazachok.workmanager.ui.theme.WorkManagerTheme

class MainActivity : ComponentActivity() {

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WorkManagerTheme {
                FileScreen()
            }
        }
    }
}