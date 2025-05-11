package com.practice.quotesapp.data.wm

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.practice.quotesapp.R
import com.practice.quotesapp.data.local.QuoteDao
import com.practice.quotesapp.data.mapper.toDomain
import com.practice.quotesapp.data.remote.QuotesApi
import com.practice.quotesapp.utils.QuotesConstant
import com.practice.quotesapp.utils.QuotesConstant.Notification.CHANNEL
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PeriodicWorker @AssistedInject constructor(
   @Assisted private val context: Context,
   @Assisted private val workerParameters: WorkerParameters,
    private val quotesApi: QuotesApi,
    private val quoteDao: QuoteDao
) : CoroutineWorker(appContext = context, workerParameters) {

    override suspend fun doWork(): Result {
        //going to hit backend to prevent from crash using this
        return try {
            val response = quotesApi.getQuotes().toDomain(QuotesConstant.WM.PERIODIC_WORK_REQ)
            quoteDao.insert(response)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val notification = NotificationCompat.Builder(context, CHANNEL)
                        .setSmallIcon(R.drawable.quotes)
                        .setContentTitle("Quote's")
                        .setContentText(response.quote.plus(" ${response.author}"))
                        .build()
                    NotificationManagerCompat.from(context)
                        .notify(1, notification)
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}