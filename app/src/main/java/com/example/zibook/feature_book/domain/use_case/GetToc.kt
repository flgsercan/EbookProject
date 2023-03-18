package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.TocItem
import com.example.zibook.feature_book.domain.repository.BookRepository

class GetToc(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Long): TocItem? {
        return repository.getTocById(id)
    }
}