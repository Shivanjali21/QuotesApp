package com.practice.quotesapp.data.mapper

import com.practice.quotesapp.data.model.QuotesDTO
import com.practice.quotesapp.domain.model.Quote

fun QuotesDTO.toDomain(workType: String): Quote{
  return Quote(author = author, id = id, quote = quote, workType = workType)
}