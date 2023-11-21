package com.yuliakazachok.workmanager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuliakazachok.workmanager.domain.repository.FileRepository
import kotlinx.coroutines.launch

class FileViewModel(private val fileRepository: FileRepository) : ViewModel() {

    fun createFile() {
        viewModelScope.launch {
            fileRepository.create()
        }
    }
}