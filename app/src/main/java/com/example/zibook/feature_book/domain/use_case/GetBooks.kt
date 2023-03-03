package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.repository.BookRepository
import com.example.zibook.feature_book.domain.util.BookOrder
import com.example.zibook.feature_book.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBooks(private val repository: BookRepository) {

    operator fun invoke(
        bookOrder: BookOrder = BookOrder.Title(OrderType.Descending)
    ): Flow<List<Book>> {
        return repository.getBooks().map { books ->
            when(bookOrder.orderType) {
                is OrderType.Ascending -> {
                    when(bookOrder) {
                        is BookOrder.Title -> books.sortedBy { it.title.lowercase() }
                        is BookOrder.Author -> books.sortedBy { it.author.lowercase() }
                    }
                }
                is OrderType.Descending -> {
                    when(bookOrder) {
                        is BookOrder.Title -> books.sortedByDescending { it.title.lowercase() }
                        is BookOrder.Author -> books.sortedByDescending { it.author.lowercase() }
                    }
                }
            }

        }
    }
}