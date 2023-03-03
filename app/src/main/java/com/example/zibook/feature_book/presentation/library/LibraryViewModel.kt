package com.example.zibook.feature_book.presentation.library

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zibook.feature_book.data.data_source.populateEpubList
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.use_case.BookUseCases
import com.example.zibook.feature_book.domain.use_case.DocumentUseCases
import com.example.zibook.feature_book.domain.util.BookOrder
import com.example.zibook.feature_book.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val documentUseCases: DocumentUseCases,
    private val bookUseCases: BookUseCases
): ViewModel() {
    private val _state = mutableStateOf(LibraryState())
    val state: State<LibraryState> = _state

    private var recentlyDeletedBook: Book? = null

    private var getBooksJob: Job? = null

    init {
        getBooks(BookOrder.Title(OrderType.Descending))
    }

    fun onEvent(event: LibraryEvent) {
        when(event) {
            is LibraryEvent.Order -> {
                if (
                    state.value.bookOrder::class == event.bookOrder::class &&
                    state.value.bookOrder.orderType == event.bookOrder.orderType
                ) {
                    return
                }
                getBooks(event.bookOrder)
            }
            is LibraryEvent.DeleteBook -> {
                viewModelScope.launch {
                    bookUseCases.deleteBook(event.book)
                    recentlyDeletedBook = event.book
                }
            }
            is LibraryEvent.RestoreBook -> {
                viewModelScope.launch {
                    bookUseCases.addBook(recentlyDeletedBook ?: return@launch)
                    recentlyDeletedBook = null
                }
            }
            is LibraryEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is LibraryEvent.ScanForBooks -> {
                    viewModelScope.launch {
                        populateEpubList(documentUseCases, bookUseCases)
                    }
            }
        }
    }

    private fun getBooks(bookOrder: BookOrder) {
        getBooksJob?.cancel()
        getBooksJob = bookUseCases.getBooks(bookOrder)
            .onEach { books ->
                _state.value = state.value.copy(
                    books = books,
                    bookOrder = bookOrder
                )
            }
            .launchIn(viewModelScope)
    }
}