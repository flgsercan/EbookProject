package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.TocItem
import com.example.zibook.feature_book.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetTocs(
    private val repository: BookRepository
) {
    operator fun invoke(bookId: Int): Flow<List<TocItem>> {
        return repository.getToc(bookId)
    }
}