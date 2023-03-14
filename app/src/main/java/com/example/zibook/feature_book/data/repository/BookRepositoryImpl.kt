package com.example.zibook.feature_book.data.repository

import com.example.zibook.feature_book.data.data_source.BookDao
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.model.TocItem
import com.example.zibook.feature_book.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(
    private val dao: BookDao
) : BookRepository {
    override fun getBooks(limit: Int, id: Int): Flow<List<Book>> {
        return dao.getBooks(limit, id)
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

    override fun getToc(bookId: Int): Flow<List<TocItem>> {
        return dao.getToc(bookId)
    }

    override suspend fun getTocById(id: Int): TocItem? {
        return dao.getTocById(id)
    }

    override suspend fun getTocByUrl(url: String): TocItem? {
        return dao.getTocByUrl(url)
    }

    override suspend fun insertToc(tocItem: TocItem) {
        dao.insertToc(tocItem)
    }

    override suspend fun deleteToc(tocItem: TocItem) {
        dao.deleteToc(tocItem)
    }

    override suspend fun insertSpine(spineItem: SpineItem) {
        dao.insertSpine(spineItem)
    }

    override fun getSpine(bookId: Int): Flow<List<SpineItem>> {
        return dao.getSpine(bookId)
    }

    override suspend fun getSpineById(id: Int): SpineItem? {
        return dao.getSpineById(id)
    }
    override suspend fun getSpineByUrl(url : String): SpineItem? {
        return dao.getSpineByUrl(url)
    }

    override suspend fun deleteSpine(spineItem: SpineItem) {
        dao.deleteSpine(spineItem)
    }
}