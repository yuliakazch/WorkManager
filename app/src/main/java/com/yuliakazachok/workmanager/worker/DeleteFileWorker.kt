package com.yuliakazachok.workmanager.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yuliakazachok.workmanager.FILE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DeleteFileWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val file = File(applicationContext.filesDir, FILE_NAME)
                file.delete()

                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }
}