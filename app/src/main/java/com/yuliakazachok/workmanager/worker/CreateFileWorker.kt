package com.yuliakazachok.workmanager.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CreateFileWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    private companion object {

        const val DELAY_TIME_MILLIS = 10000L
        const val FILE_NAME = "app_file"
    }

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            delay(DELAY_TIME_MILLIS)

            try {
                createFile(applicationContext, getFormattedTime())
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }

    private fun getFormattedTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    private fun createFile(applicationContext: Context, data: String) {
        val file = File(applicationContext.filesDir, FILE_NAME)
        file.createNewFile()
        file.outputStream().bufferedWriter().use { it.write(data) }
    }
}