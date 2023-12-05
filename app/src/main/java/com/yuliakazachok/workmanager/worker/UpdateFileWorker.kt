package com.yuliakazachok.workmanager.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yuliakazachok.workmanager.DELAY_TIME_MILLIS
import com.yuliakazachok.workmanager.FILE_NAME
import com.yuliakazachok.workmanager.util.TimeFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Date

class UpdateFileWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val formattedTime = TimeFormatter.format(currentDate = Date())

                delay(DELAY_TIME_MILLIS)

                updateFile(applicationContext, formattedTime)
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }

    private fun updateFile(applicationContext: Context, formattedTime: String) {
        val file = File(applicationContext.filesDir, FILE_NAME)
        if (file.exists()) {
            file.appendText("\n" + formattedTime)
        }
    }
}