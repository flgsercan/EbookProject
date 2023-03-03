package com.example.zibook.feature_book.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zibook.feature_book.domain.use_case.BookUseCases
import com.example.zibook.feature_book.domain.use_case.DocumentUseCases
import com.example.zibook.feature_book.domain.util.BookOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
    private val documentUseCases: DocumentUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state
    private var getBooksJob: Job? = null

    init {
        savedStateHandle.get<Int>("bookId")?.let { bookId ->
            if (bookId != -1) {
                getToc(bookId)
                viewModelScope.launch {
                    bookUseCases.getBookById(bookId).also { book ->
                        if (book != null) {
                            _state.value = _state.value.copy(
                                book = book
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getToc(bookId: Int) {
        getBooksJob?.cancel()
        getBooksJob = bookUseCases.getToc(bookId)
            .onEach {  navItemList ->
                _state.value = state.value.copy(
                    navItemList = navItemList
                )
            }
            .launchIn(viewModelScope)
    }
}