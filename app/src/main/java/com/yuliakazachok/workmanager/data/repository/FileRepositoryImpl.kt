package com.yuliakazachok.workmanager.data.repository

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.yuliakazachok.workmanager.DATA_KEY
import com.yuliakazachok.workmanager.worker.CreateFileWorker
import com.yuliakazachok.workmanager.domain.repository.FileRepository
import com.yuliakazachok.workmanager.worker.DeleteFileWorker
import com.yuliakazachok.workmanager.worker.FormOutputDataWorker

class FileRepositoryImpl(private val workManager: WorkManager) : FileRepository {

    private companion object {

        const val CREATE_FILE_WORK_NAME = "createFileWork"
    }

    override suspend fun create(data: String) {
        val deleteFileBuilder = OneTimeWorkRequestBuilder<DeleteFileWorker>()
        val formOutputDataBuilder = OneTimeWorkRequestBuilder<FormOutputDataWorker>()
            .setInputData(workDataOf(DATA_KEY to data))
        val createFileBuilder = OneTimeWorkRequestBuilder<CreateFileWorker>()

        workManager.beginUniqueWork(CREATE_FILE_WORK_NAME, ExistingWorkPolicy.REPLACE, deleteFileBuilder.build())
            .then(formOutputDataBuilder.build())
            .then(createFileBuilder.build())
            .enqueue()
    }

    override suspend fun delete() {
        val deleteFileBuilder = OneTimeWorkRequestBuilder<DeleteFileWorker>()

        workManager.enqueue(deleteFileBuilder.build())
    }
}