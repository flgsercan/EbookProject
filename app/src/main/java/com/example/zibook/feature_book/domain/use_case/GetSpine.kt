package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.repository.BookRepository


class GetSpine(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Int): SpineItem? {
        return repository.getSpineById(id)
    }
    suspend operator fun invoke(url: String): SpineItem? {
        return repository.getSpineByUrl(url)
    }
}