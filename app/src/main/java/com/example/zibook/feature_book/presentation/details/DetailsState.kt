package com.example.zibook.feature_book.presentation.details

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.EpubBook
import com.example.zibook.feature_book.domain.model.TocItem

data class DetailsState(
    val book: Book? = null,
    val epubFile: EpubBook? = null,
    val tocItemList: List<TocItem>? = null,
    val tocItem: TocItem? = null,
    val hover: String = "nll"
)
