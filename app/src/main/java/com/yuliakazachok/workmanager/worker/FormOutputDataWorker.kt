package com.yuliakazachok.workmanager.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.yuliakazachok.workmanager.DATA_KEY
import com.yuliakazachok.workmanager.DELAY_TIME_MILLIS
import com.yuliakazachok.workmanager.TIME_KEY
import com.yuliakazachok.workmanager.util.TimeFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.Date

class FormOutputDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val data = inputData.getString(DATA_KEY)
                val time = TimeFormatter.format(currentDate = Date())

                delay(DELAY_TIME_MILLIS)

                val outputData = workDataOf(TIME_KEY to getOutputText(data, time))
                Result.success(outputData)
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }

    private fun getOutputText(data: String?, time: String?): String =
        listOfNotNull(data, time).joinToString("\n")
}