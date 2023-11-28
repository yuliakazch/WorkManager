package com.yuliakazachok.workmanager.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yuliakazachok.workmanager.R
import com.yuliakazachok.workmanager.presentation.FileViewModel
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun FileScreen(viewModel: FileViewModel = koinViewModel()) {
    val data = viewModel.data.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(bottom = 16.dp),
            value = data.value,
            onValueChange = viewModel::changeData,
            label = { Text(stringResource(R.string.data_to_save)) },
        )
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                onClick = viewModel::createFile,
            ) {
                Text(stringResource(R.string.create_file))
            }
            Button(
                onClick = viewModel::cancelCreatingFile,
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
        Button(
            modifier = Modifier.padding(bottom = 8.dp),
            onClick = viewModel::deleteFile,
        ) {
            Text(stringResource(R.string.delete_file))
        }
    }
}