package com.example.zibook.feature_book.domain.repository

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.model.TocItem
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(limit: Int, id: Long): Flow<List<Book>>
    suspend fun getBookById(id: Long): Book?
    suspend fun insertBook(book: Book): Long
    suspend fun deleteBook(book: Book)
    suspend fun getBookByPath(path: String): Book?

    fun getToc(bookId: Long): Flow<List<TocItem>>
    suspend fun getTocById(id: Long): TocItem?
    suspend fun getTocByUrl(url : String): TocItem?
    suspend fun insertToc(tocItem: TocItem): Long
    suspend fun deleteToc(tocItem: TocItem)

    suspend fun insertSpine(spineItem: SpineItem): Long
    fun getSpine(bookId: Long, tocId: Long): Flow<List<SpineItem>>
    suspend fun getSpineById(id: Long): SpineItem?
    suspend fun getSpineByUrl(url : String): SpineItem?
    suspend fun deleteSpine(spineItem: SpineItem)
}