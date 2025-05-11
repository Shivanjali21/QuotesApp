package com.practice.quotesapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.practice.quotesapp.utils.QuotesConstant.Notification.CHANNEL
import com.practice.quotesapp.utils.QuotesConstant.Notification.NAME
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class QuoteApplication : Application() {

    //disable default things coming from WM

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(
            this, Configuration.Builder().setWorkerFactory(
                hiltWorkerFactory
            ).build()
        )

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
          val notificationChannel = NotificationChannel(CHANNEL,NAME, NotificationManager.IMPORTANCE_HIGH)
          val nm = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

          nm.createNotificationChannel(
             notificationChannel
          )
        }
    }
}