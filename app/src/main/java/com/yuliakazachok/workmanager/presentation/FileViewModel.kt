package com.yuliakazachok.workmanager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuliakazachok.workmanager.domain.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FileViewModel(private val fileRepository: FileRepository) : ViewModel() {

    private val _data = MutableStateFlow("")
    val data: StateFlow<String> = _data

    fun createFile() {
        viewModelScope.launch {
            fileRepository.create(_data.value)
        }
    }

    fun changeData(data: String) {
        _data.value = data
    }

    fun deleteFile() {
        viewModelScope.launch {
            fileRepository.delete()
        }
    }
}