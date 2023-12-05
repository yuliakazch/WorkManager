package com.yuliakazachok.workmanager.presentation

import androidx.lifecycle.ViewModel
import com.yuliakazachok.workmanager.domain.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FileViewModel(private val fileRepository: FileRepository) : ViewModel() {

    private val _data = MutableStateFlow("")
    val data: StateFlow<String> = _data

    fun createFile() {
        fileRepository.create(_data.value)
    }

    fun changeData(data: String) {
        _data.value = data
    }

    fun deleteFile() {
        fileRepository.delete()
    }

    fun cancelCreatingFile() {
        fileRepository.cancelCreating()
    }

    fun updateFile() {
        fileRepository.updatePeriodic()
    }
}