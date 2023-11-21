package com.yuliakazachok.workmanager.data.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.yuliakazachok.workmanager.DATA_KEY
import com.yuliakazachok.workmanager.worker.CreateFileWorker
import com.yuliakazachok.workmanager.domain.repository.FileRepository

class FileRepositoryImpl(context: Context) : FileRepository {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun create(data: String) {
        val createFileBuilder = OneTimeWorkRequestBuilder<CreateFileWorker>()

        createFileBuilder.setInputData(workDataOf(DATA_KEY to data))

        workManager.enqueue(createFileBuilder.build())
    }
}