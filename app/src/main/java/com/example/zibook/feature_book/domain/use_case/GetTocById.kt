package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.NavItem
import com.example.zibook.feature_book.domain.repository.BookRepository

class GetTocById(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Int): NavItem? {
        return repository.getTocById(id)
    }
}