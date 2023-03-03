package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.EpubBook
import com.example.zibook.feature_book.domain.repository.DocumentRepository

class GetDocuments(private val repository: DocumentRepository) {

    suspend operator fun invoke(): List<EpubBook> {
        return repository.getDocuments()
    }
}