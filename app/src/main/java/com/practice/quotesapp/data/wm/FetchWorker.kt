package com.practice.quotesapp.data.wm

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.practice.quotesapp.data.local.QuoteDao
import com.practice.quotesapp.data.mapper.toDomain
import com.practice.quotesapp.data.remote.QuotesApi
import com.practice.quotesapp.utils.QuotesConstant
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class FetchWorker @AssistedInject constructor(
   @Assisted private val context: Context,
   @Assisted private val workerParameters: WorkerParameters,
    private val quotesApi: QuotesApi,
    private val quoteDao: QuoteDao
) : CoroutineWorker(appContext = context, workerParameters) {

    override suspend fun doWork(): Result {
        //going to hit backend to prevent from crash using this
        return try {
            val response = quotesApi.getQuotes().toDomain(QuotesConstant.WM.ONE_TIME_REQ)
            quoteDao.insert(response)
            val data = Data.Builder()
                .putString(QuotesConstant.Notification.QUOTE, Gson().toJson(response)).build()
            Result.success(data)
        } catch (e: Exception) {
            Result.failure()
        }
    }
}