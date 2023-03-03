package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.repository.BookRepository


class GetBookById(
    private val repository: BookRepository
) {

    suspend operator fun invoke(id: Int): Book? {
        return repository.getBookById(id)
    }
}