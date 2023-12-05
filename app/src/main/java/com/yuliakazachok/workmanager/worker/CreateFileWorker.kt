package com.yuliakazachok.workmanager.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.yuliakazachok.workmanager.DELAY_TIME_MILLIS
import com.yuliakazachok.workmanager.FILE_NAME
import com.yuliakazachok.workmanager.TIME_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File


class CreateFileWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val outputData = inputData.getString(TIME_KEY) ?: ""

                delay(DELAY_TIME_MILLIS)

                createFile(applicationContext, outputData)
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }

    private fun createFile(applicationContext: Context, outputText: String) {
        val file = File(applicationContext.filesDir, FILE_NAME)
        file.createNewFile()
        file.outputStream().bufferedWriter().use { it.write(outputText) }
    }
}