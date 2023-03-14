package com.example.zibook.di

import android.app.Application
import androidx.room.Room
import com.example.zibook.feature_book.data.data_source.BookDatabase
import com.example.zibook.feature_book.data.repository.BookRepositoryImpl
import com.example.zibook.feature_book.data.repository.DocumentDaoImpl
import com.example.zibook.feature_book.data.repository.DocumentRepositoryImpl
import com.example.zibook.feature_book.domain.repository.BookRepository
import com.example.zibook.feature_book.domain.repository.DocumentRepository
import com.example.zibook.feature_book.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookDatabase(app: Application): BookDatabase {
        return Room.databaseBuilder(
            app,
            BookDatabase::class.java,
            BookDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookRepository(db: BookDatabase): BookRepository {
        return BookRepositoryImpl(db.bookDao)
    }

    @Provides
    @Singleton
    fun provideDocumentRepository(): DocumentRepository {
        return DocumentRepositoryImpl(dao = DocumentDaoImpl())
    }

    @Provides
    @Singleton
    fun BookUseCases(repository: BookRepository): BookUseCases {
        return BookUseCases(
            getBooks = GetBooks(repository),
            deleteBook = DeleteBook(repository),
            addBook = AddBook(repository),
            getBookById = GetBookById(repository),
            getBookByPath = GetBookByPath(repository),
            getTocs = GetTocs(repository),
            getToc = GetToc(repository),
            deleteToc = DeleteToc(repository),
            addToc = AddToc(repository),
            addSpineItem = AddSpineItem(repository),
            getSpines = GetSpines(repository),
            getSpine = GetSpine(repository),
            deleteSpine = DeleteSpine(repository)
        )
    }
    @Provides
    @Singleton
    fun DocumentUseCases(repository: DocumentRepository): DocumentUseCases {
        return DocumentUseCases(
            getDocuments = GetDocuments(repository),
            getDocumentByPath = GetDocumentByPath(repository),
            unzipDocument = UnzipDocument(repository),
            getTocByPath = GetTocByPath(repository)
        )
    }
}