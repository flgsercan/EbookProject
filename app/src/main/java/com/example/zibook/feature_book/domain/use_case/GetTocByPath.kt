package com.example.zibook.feature_book.domain.use_case

import com.example.zibook.feature_book.domain.model.NavigationItemModel
import com.example.zibook.feature_book.domain.repository.DocumentRepository


class GetTocByPath(private val repository: DocumentRepository) {

    suspend operator fun invoke(path: String) : List<NavigationItemModel> {

        val tocList = mutableListOf<NavigationItemModel>()
        repository.getDocumentByPath(path).epubTableOfContentsModel
            ?.tableOfContents
            ?.forEach { navItem ->
                if (navItem.subItems == null) {
                    tocList.add(navItem)
                } else {
                    tocList.add(navItem)
                    recursiveSort(navItem.subItems).forEach {
                        tocList.add(it)
                    }
                }
        }
        return tocList.toList()
    }
    private fun recursiveSort(nav: List<NavigationItemModel>) : List<NavigationItemModel> {

        val tocList = mutableListOf<NavigationItemModel>()
        nav.forEach { item ->
            if (item.subItems == null) {
                tocList.add(item)
            } else {
                tocList.add(item)
                recursiveSort(item.subItems).forEach {
                    tocList.add(it)
                }
            }
        }
        return tocList.toList()
    }
}