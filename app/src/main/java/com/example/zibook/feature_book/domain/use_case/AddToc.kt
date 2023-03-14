package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.TocItem
import com.example.zibook.feature_book.domain.repository.BookRepository

class AddToc(
    private val repository: BookRepository
) {

    suspend operator fun invoke(tocItem: TocItem) {
        repository.insertToc(tocItem)
    }
}