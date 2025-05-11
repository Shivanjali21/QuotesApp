package com.practice.quotesapp.data.di

import android.content.Context
import androidx.work.WorkManager
import com.practice.quotesapp.data.local.QuoteDB
import com.practice.quotesapp.data.local.QuoteDao
import com.practice.quotesapp.data.remote.QuotesApi
import com.practice.quotesapp.data.repo.QuoteRepoImpl
import com.practice.quotesapp.domain.repo.QuotesRepo
import com.practice.quotesapp.utils.QuotesConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(QuotesConstant.URL.BASEURL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): QuotesApi {
        return retrofit.create(QuotesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): QuoteDB {
        return QuoteDB.getInstance(context = context)
    }

    @Provides
    fun provideQuoteDao(quoteDatabase: QuoteDB): QuoteDao {
        return quoteDatabase.getQuoteDao()
    }

    @Singleton
    @Provides
    fun workManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context = context)
    }

    @Provides
    fun provideQuoteRepository(workManager: WorkManager, quoteDao: QuoteDao): QuotesRepo =
        QuoteRepoImpl(workManager = workManager, quoteDao = quoteDao)
}