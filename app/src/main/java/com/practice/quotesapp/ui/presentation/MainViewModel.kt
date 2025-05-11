package com.practice.quotesapp.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.quotesapp.domain.model.Quote
import com.practice.quotesapp.domain.usecase.GetAllQuotesFromDbUseCase
import com.practice.quotesapp.domain.usecase.GetQuoteUseCase
import com.practice.quotesapp.domain.usecase.SetupPeriodicWRUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  getAllQuotesFromDbUseCase: GetAllQuotesFromDbUseCase,
  private val getQuoteUseCase: GetQuoteUseCase,
  setupPeriodicWRUseCase: SetupPeriodicWRUseCase
): ViewModel() {

   val uiState = getAllQuotesFromDbUseCase.invoke()
       .map { UiState(it) }
       .stateIn(viewModelScope, SharingStarted.Eagerly, UiState(emptyList()))
   init {
     setupPeriodicWRUseCase.invoke()
   }

    fun getQuote() = getQuoteUseCase.invoke()
}

data class UiState(val data:List<Quote>)