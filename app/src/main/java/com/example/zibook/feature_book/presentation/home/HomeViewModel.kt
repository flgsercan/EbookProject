package com.example.zibook.feature_book.presentation.home

import androidx.lifecycle.ViewModel
import com.example.zibook.feature_book.domain.use_case.BookUseCases
import com.example.zibook.feature_book.domain.use_case.DocumentUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val bookUseCases: BookUseCases
) : ViewModel() {

}