package com.yuliakazachok.workmanager.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yuliakazachok.workmanager.R
import com.yuliakazachok.workmanager.presentation.FileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FileScreen(viewModel: FileViewModel = koinViewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = viewModel::createFile) {
            Text(stringResource(R.string.create_file))
        }
    }
}