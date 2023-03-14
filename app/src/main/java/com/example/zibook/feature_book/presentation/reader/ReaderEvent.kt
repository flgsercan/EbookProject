package com.example.zibook.feature_book.presentation.reader

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.util.BookOrder

sealed class ReaderEvent {
    object NextChapter: ReaderEvent()
    object PreviousChapter: ReaderEvent()
}
