package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.NavItem
import com.example.zibook.feature_book.domain.repository.BookRepository

class DeleteToc(
    private val repository: BookRepository
) {
    suspend operator fun invoke(navItem: NavItem) {
        repository.deleteToc(navItem)
    }
}