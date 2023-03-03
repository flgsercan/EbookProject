package com.example.zibook.feature_book.data.repository

import com.example.zibook.feature_book.data.data_source.BookDao
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.NavItem
import com.example.zibook.feature_book.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(
    private val dao: BookDao
) : BookRepository {
    override fun getBooks(): Flow<List<Book>> {
        return dao.getBooks()
    }

    override suspend fun getBookById(id: Int): Book? {
        return dao.getBookById(id)
    }

    override suspend fun insertBook(book: Book) {
        dao.insertBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        dao.deleteBook(book)
    }

    override suspend fun getBookByPath(path: String): Book? {
        return dao.getBookByPath(path)
    }

    override fun getToc(bookId: Int): Flow<List<NavItem>> {
        return dao.getToc(bookId)
    }

    override suspend fun getTocById(id: Int): NavItem? {
        return dao.getTocById(id)
    }

    override suspend fun insertToc(navItem: NavItem) {
        dao.insertToc(navItem)
    }

    override suspend fun deleteToc(navItem: NavItem) {
        dao.deleteToc(navItem)
    }
}