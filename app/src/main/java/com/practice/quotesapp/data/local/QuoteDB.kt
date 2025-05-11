package com.practice.quotesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.quotesapp.domain.model.Quote

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuoteDB : RoomDatabase() {

   companion object {
       fun getInstance(context: Context) =
         Room.databaseBuilder(context, QuoteDB::class.java,"quote_db")
             .build()
   }

  abstract fun getQuoteDao(): QuoteDao
}