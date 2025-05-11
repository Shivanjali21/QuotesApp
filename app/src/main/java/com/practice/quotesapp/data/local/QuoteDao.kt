package com.practice.quotesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.quotesapp.domain.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(quote: Quote)

  @Query("SELECT * FROM QUOTE ORDER BY time DESC ")
  fun getAllQuotes(): Flow<List<Quote>>
}