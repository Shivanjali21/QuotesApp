package com.practice.quotesapp.data.wm

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.practice.quotesapp.R
import com.practice.quotesapp.domain.model.Quote
import com.practice.quotesapp.utils.QuotesConstant
import com.practice.quotesapp.utils.QuotesConstant.Notification.CHANNEL

class NotificationWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val quoteJson =
                    workerParameters.inputData.getString(QuotesConstant.Notification.QUOTE)
                val quote = Gson().fromJson(quoteJson, Quote::class.java)
                val notification = NotificationCompat.Builder(context, CHANNEL)
                    .setSmallIcon(R.drawable.quotes)
                    .setContentTitle("Quote's")
                    .setContentText(quote.quote.plus(" ${quote.author}"))
                    .build()
                NotificationManagerCompat.from(context)
                    .notify(1, notification)
            }
        }
        return Result.success()
    }
}