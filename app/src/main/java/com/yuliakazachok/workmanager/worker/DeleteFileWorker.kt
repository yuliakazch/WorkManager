package com.yuliakazachok.workmanager.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat.Builder
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.yuliakazachok.workmanager.FILE_NAME
import com.yuliakazachok.workmanager.DELETING_FILE_NOTIFICATION_ID
import com.yuliakazachok.workmanager.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class DeleteFileWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    private val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

    override suspend fun getForegroundInfo(): ForegroundInfo =
        ForegroundInfo(DELETING_FILE_NOTIFICATION_ID, createNotification())

    private fun createNotification(): Notification {
        val channelId = applicationContext.getString(R.string.notification_channel_id)
        val channelName = applicationContext.getString(R.string.notification_channel_name)
        val title = applicationContext.getString(R.string.notification_title_deleting_file)

        val builder = Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)

            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channel.id)
        }

        return builder.build()
    }
}