package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetSpines(
    private val repository: BookRepository
) {
    operator fun invoke(bookId: Long, tocId: Long): Flow<List<SpineItem>> {
        return repository.getSpine(bookId, tocId)
    }
}