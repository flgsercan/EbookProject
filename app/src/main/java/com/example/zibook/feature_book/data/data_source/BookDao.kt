package com.example.zibook.feature_book.data.data_source

import androidx.room.*
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.NavItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBookById(id : Int): Book?

    @Query("SELECT * FROM book WHERE path = :path")
    suspend fun getBookByPath(path : String): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM toc WHERE bookId = :bookId")
    fun getToc(bookId: Int): Flow<List<NavItem>>

    @Query("SELECT * FROM toc WHERE id = :id")
    suspend fun getTocById(id : Int): NavItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToc(navItem: NavItem)

    @Delete
    suspend fun deleteToc(navItem: NavItem)

}