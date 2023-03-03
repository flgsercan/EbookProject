package com.example.zibook.feature_book.domain.repository

import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.NavItem
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBooks(): Flow<List<Book>>
    suspend fun getBookById(id: Int): Book?
    suspend fun insertBook(book: Book)
    suspend fun deleteBook(book: Book)
    suspend fun getBookByPath(path: String): Book?

    fun getToc(bookId: Int): Flow<List<NavItem>>
    suspend fun getTocById(id: Int): NavItem?
    suspend fun insertToc(navItem: NavItem)
    suspend fun deleteToc(navItem: NavItem)
}