package com.practice.quotesapp.domain.repo

import com.practice.quotesapp.domain.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesRepo {
   fun getQuotes()
   fun getAllQuotes(): Flow<List<Quote>>
   fun setUpPeriodicWorkRequest()
}