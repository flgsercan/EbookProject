package com.example.zibook.feature_book.presentation.reader

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.model.TocItem

data class ReaderState(
    val data: Output? = null,
    val tocId: Long? = null,
    val book: Book? = null,
    val location: String? = null,
)
data class Output(val title: String?, val body: List<String>)