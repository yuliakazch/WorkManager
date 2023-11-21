package com.yuliakazachok.workmanager.data.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yuliakazachok.workmanager.worker.CreateFileWorker
import com.yuliakazachok.workmanager.domain.repository.FileRepository

class FileRepositoryImpl(context: Context) : FileRepository {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun create() {
        val createFileBuilder = OneTimeWorkRequestBuilder<CreateFileWorker>()

        workManager.enqueue(createFileBuilder.build())
    }
}