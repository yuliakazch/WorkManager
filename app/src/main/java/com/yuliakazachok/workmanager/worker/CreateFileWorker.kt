package com.yuliakazachok.workmanager.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.yuliakazachok.workmanager.CREATING_FILE_NOTIFICATION_ID
import com.yuliakazachok.workmanager.DELAY_TIME_MILLIS
import com.yuliakazachok.workmanager.FILE_NAME
import com.yuliakazachok.workmanager.R
import com.yuliakazachok.workmanager.TIME_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File


class CreateFileWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    private val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                setForeground(createForegroundInfo())

                val outputData = inputData.getString(TIME_KEY) ?: ""

                delay(DELAY_TIME_MILLIS)

                createFile(applicationContext, outputData)
                Result.success()
            } catch (throwable: Throwable) {
                Result.failure()
            }
        }

    private fun createForegroundInfo(): ForegroundInfo {
        val channelId = applicationContext.getString(R.string.notification_channel_id)
        val channelName = applicationContext.getString(R.string.notification_channel_name)
        val title = applicationContext.getString(R.string.notification_title_creating_file)

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)

            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channel.id)
        }

        return ForegroundInfo(CREATING_FILE_NOTIFICATION_ID, builder.build())
    }

    private fun createFile(applicationContext: Context, outputText: String) {
        val file = File(applicationContext.filesDir, FILE_NAME)
        file.createNewFile()
        file.outputStream().bufferedWriter().use { it.write(outputText) }
    }
}