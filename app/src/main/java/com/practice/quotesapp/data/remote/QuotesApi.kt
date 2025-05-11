package com.practice.quotesapp.data.remote

import com.practice.quotesapp.data.model.QuotesDTO
import retrofit2.http.GET

interface QuotesApi {

    @GET("quotes/random")
    suspend fun getQuotes(): QuotesDTO
}
