package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.model.TocItem
import com.example.zibook.feature_book.domain.repository.BookRepository


class AddSpineItem(
    private val repository: BookRepository
) {

    suspend operator fun invoke(spineItem: SpineItem): Long {
        return repository.insertSpine(spineItem)
    }
}