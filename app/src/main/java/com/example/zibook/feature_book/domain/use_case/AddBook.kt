package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.repository.BookRepository


class AddBook(
    private val repository: BookRepository
) {

    suspend operator fun invoke(book: Book): Long {
        return repository.insertBook(book)
    }
}