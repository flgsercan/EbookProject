package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.repository.BookRepository

class GetBookByPath(
    private val repository: BookRepository
) {
    suspend operator fun invoke(path: String): Book? {
        return repository.getBookByPath(path)
    }
}