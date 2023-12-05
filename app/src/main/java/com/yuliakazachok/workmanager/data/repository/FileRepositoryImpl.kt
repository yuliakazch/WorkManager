package com.yuliakazachok.workmanager.data.repository

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.yuliakazachok.workmanager.BACKOFF_DELAY_MILLIS
import com.yuliakazachok.workmanager.DATA_KEY
import com.yuliakazachok.workmanager.DELAY_INITIAL_MINUTES
import com.yuliakazachok.workmanager.PERIODIC_INTERVAL_MINUTES
import com.yuliakazachok.workmanager.worker.CreateFileWorker
import com.yuliakazachok.workmanager.domain.repository.FileRepository
import com.yuliakazachok.workmanager.worker.DeleteFileWorker
import com.yuliakazachok.workmanager.worker.FormOutputDataWorker
import com.yuliakazachok.workmanager.worker.UpdateFileWorker
import java.util.concurrent.TimeUnit

class FileRepositoryImpl(private val workManager: WorkManager) : FileRepository {

    private companion object {

        const val CREATE_FILE_WORK_NAME = "createFileWork"
    }

    override fun create(data: String) {
        val deleteFileBuilder = OneTimeWorkRequestBuilder<DeleteFileWorker>()
        val formOutputDataBuilder = OneTimeWorkRequestBuilder<FormOutputDataWorker>()
            .setInputData(workDataOf(DATA_KEY to data))
        val createFileBuilder = OneTimeWorkRequestBuilder<CreateFileWorker>()

        val constraintsForCreateFile = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        workManager.beginUniqueWork(CREATE_FILE_WORK_NAME, ExistingWorkPolicy.REPLACE, deleteFileBuilder.build())
            .then(formOutputDataBuilder.build())
            .then(createFileBuilder.setConstraints(constraintsForCreateFile).build())
            .enqueue()
    }

    override fun delete() {
        val deleteFileBuilder = OneTimeWorkRequestBuilder<DeleteFileWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)

        workManager.enqueue(deleteFileBuilder.build())
    }

    override fun cancelCreating() {
        workManager.cancelUniqueWork(CREATE_FILE_WORK_NAME)
    }

    override fun updatePeriodic() {
        val updateFileBuilder = PeriodicWorkRequestBuilder<UpdateFileWorker>(PERIODIC_INTERVAL_MINUTES, TimeUnit.MINUTES)
            .setInitialDelay(DELAY_INITIAL_MINUTES, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, BACKOFF_DELAY_MILLIS, TimeUnit.MILLISECONDS)

        workManager.enqueue(updateFileBuilder.build())
    }
}