package com.practice.quotesapp.data.repo

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.practice.quotesapp.data.local.QuoteDao
import com.practice.quotesapp.data.wm.FetchWorker
import com.practice.quotesapp.data.wm.NotificationWorker
import com.practice.quotesapp.data.wm.PeriodicWorker
import com.practice.quotesapp.domain.model.Quote
import com.practice.quotesapp.domain.repo.QuotesRepo
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class QuoteRepoImpl(private val workManager:WorkManager,
    private val quoteDao: QuoteDao) : QuotesRepo {

    override fun getQuotes() {
      val constraints = Constraints.Builder()
          .setRequiredNetworkType(networkType = NetworkType.CONNECTED).build()
      val workRequest = OneTimeWorkRequestBuilder<FetchWorker>()
          .setConstraints(constraints = constraints).build()
      val notificationWorkReq = OneTimeWorkRequestBuilder<NotificationWorker>()
          .build()
      workManager.beginWith(workRequest).then(notificationWorkReq).enqueue()
    }

    override fun getAllQuotes(): Flow<List<Quote>> = quoteDao.getAllQuotes()

    override fun setUpPeriodicWorkRequest() {
       val constraints = Constraints.Builder()
            .setRequiredNetworkType(networkType = NetworkType.CONNECTED).build()
       val workRequest = PeriodicWorkRequest.Builder(PeriodicWorker::class.java,
              15, TimeUnit.MINUTES).setConstraints(constraints = constraints)
           .build()

       workManager.enqueueUniquePeriodicWork("quoteTask",
           ExistingPeriodicWorkPolicy.UPDATE, workRequest)
    }
}