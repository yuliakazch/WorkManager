package com.yuliakazachok.workmanager.di

import androidx.work.WorkManager
import com.yuliakazachok.workmanager.data.repository.FileRepositoryImpl
import com.yuliakazachok.workmanager.domain.repository.FileRepository
import com.yuliakazachok.workmanager.presentation.FileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<WorkManager> { WorkManager.getInstance(androidContext()) }
    single<FileRepository> { FileRepositoryImpl(get()) }
    viewModel { FileViewModel(get()) }
}