package com.example.zibook.feature_book.presentation.details

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.EpubBook
import com.example.zibook.feature_book.domain.model.NavItem

data class DetailsState(
    val book: Book? = null,
    val epubFile: EpubBook? = null,
    val navItemList: List<NavItem>? = null,
    val navItem: NavItem? = null
)
