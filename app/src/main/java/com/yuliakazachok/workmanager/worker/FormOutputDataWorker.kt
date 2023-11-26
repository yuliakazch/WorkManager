package com.yuliakazachok.workmanager.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.yuliakazachok.workmanager.DATA_KEY
import com.yuliakazachok.workmanager.DELAY_TIME_MILLIS
import com.yuliakazachok.workmanager.OUTPUT_DATA_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FormOutputDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val data = inputData.getString(DATA_KEY)
                val time = getFormattedTime()

                delay(DELAY_TIME_MILLIS)

                val outputData = workDataOf(OUTPUT_DATA_KEY to getOutputText(data, time))
                Result.success(outputData)
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }

    private fun getFormattedTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    private fun getOutputText(data: String?, time: String?): String =
        listOfNotNull(data, time).joinToString("\n")
}