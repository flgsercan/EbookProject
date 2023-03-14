package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.NavigationItemModel
import com.example.zibook.feature_book.domain.repository.DocumentRepository


class GetTocByPath(private val repository: DocumentRepository) {

    suspend operator fun invoke(path: String) : List<NavigationItemModel>? {

        return repository.getDocumentByPath(path).epubTableOfContentsModel?.tableOfContents
    }
}