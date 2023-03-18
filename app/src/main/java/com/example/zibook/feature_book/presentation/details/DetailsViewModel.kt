package com.example.zibook.feature_book.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zibook.feature_book.domain.use_case.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state
    private var getBooksJob: Job? = null

    init {
        savedStateHandle.get<Long>("bookId")?.let { bookId ->
            if (bookId != -1L) {
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

    private fun getToc(bookId: Long) {
        getBooksJob?.cancel()
        getBooksJob = bookUseCases.getTocs(bookId)
            .onEach {  navItemList ->
                _state.value = state.value.copy(
                    tocItemList = navItemList
                )
            }
            .launchIn(viewModelScope)
    }
    fun debug(bookId: Long, tocId: Long) {

        bookUseCases.getSpines(bookId, tocId).onEach {
            if(it.isNotEmpty()) {
                _state.value = _state.value.copy(
                    hover = it.first().location
                )
            }
        }.launchIn(viewModelScope)
    }
}