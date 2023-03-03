package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.NavItem
import com.example.zibook.feature_book.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetToc(
    private val repository: BookRepository
) {
    operator fun invoke(bookId: Int): Flow<List<NavItem>> {
        return repository.getToc(bookId)
    }
}