package com.practice.quotesapp.domain.usecase

import com.practice.quotesapp.domain.repo.QuotesRepo
import javax.inject.Inject

class GetAllQuotesFromDbUseCase @Inject constructor(private val
quotesRepo: QuotesRepo) {
    operator fun invoke() = quotesRepo.getAllQuotes()
}
