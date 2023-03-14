package com.example.zibook.feature_book.domain.repository

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.model.TocItem
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(limit: Int, offset: Int): Flow<List<Book>>
    suspend fun getBookById(id: Int): Book?
    suspend fun insertBook(book: Book)
    suspend fun deleteBook(book: Book)
    suspend fun getBookByPath(path: String): Book?

    fun getToc(bookId: Int): Flow<List<TocItem>>
    suspend fun getTocById(id: Int): TocItem?
    suspend fun getTocByUrl(url : String): TocItem?
    suspend fun insertToc(tocItem: TocItem)
    suspend fun deleteToc(tocItem: TocItem)

    suspend fun insertSpine(spineItem: SpineItem)
    fun getSpine(bookId: Int): Flow<List<SpineItem>>
    suspend fun getSpineById(id: Int): SpineItem?
    suspend fun getSpineByUrl(url : String): SpineItem?
    suspend fun deleteSpine(spineItem: SpineItem)
}