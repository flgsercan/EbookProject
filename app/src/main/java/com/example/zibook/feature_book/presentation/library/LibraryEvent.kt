package com.example.zibook.feature_book.presentation.library

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.util.BookOrder

sealed class LibraryEvent {
    data class Order(val bookOrder: BookOrder): LibraryEvent()
    data class  DeleteBook(val book: Book): LibraryEvent()
    object RestoreBook: LibraryEvent()
    object ToggleOrderSection: LibraryEvent()
    object ScanForBooks: LibraryEvent()
    object LoadBooksPaginated: LibraryEvent()
}
