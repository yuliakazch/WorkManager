package com.yuliakazachok.workmanager.di

import com.yuliakazachok.workmanager.data.repository.FileRepositoryImpl
import com.yuliakazachok.workmanager.domain.repository.FileRepository
import com.yuliakazachok.workmanager.presentation.FileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<FileRepository> { FileRepositoryImpl(androidContext()) }
    viewModel { FileViewModel(get()) }
}