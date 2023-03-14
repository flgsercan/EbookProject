package com.example.zibook.feature_book.presentation.library


import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.util.BookOrder
import com.example.zibook.feature_book.domain.util.OrderType

data class LibraryState(
    val books: List<Book> = emptyList(),
    val bookOrder: BookOrder = BookOrder.Title(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val endReached: Boolean = false,
    val isLoading: Boolean = false
    )
