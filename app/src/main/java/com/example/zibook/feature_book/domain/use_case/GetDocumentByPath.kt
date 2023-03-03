package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.EpubBook
import com.example.zibook.feature_book.domain.repository.DocumentRepository

class GetDocumentByPath(private val repository: DocumentRepository) {

    suspend operator fun invoke(path: String): EpubBook {
        return repository.getDocumentByPath(path)
    }
}