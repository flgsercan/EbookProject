package com.example.zibook.feature_book.data.data_source

import androidx.room.*
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.model.TocItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book WHERE id > :id ORDER BY id ASC LIMIT :limit")
    fun getBooks(limit: Int, id: Long): Flow<List<Book>>


    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBookById(id : Long): Book?

    @Query("SELECT * FROM book WHERE path = :path")
    suspend fun getBookByPath(path : String): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book): Long

    @Delete
    suspend fun deleteBook(book: Book)



    @Query("SELECT * FROM toc WHERE bookId = :bookId")
    fun getToc(bookId: Long): Flow<List<TocItem>>

    @Query("SELECT * FROM toc WHERE tocId = :id")
    suspend fun getTocById(id : Long): TocItem?
    @Query("SELECT * FROM toc WHERE location = :url")
    suspend fun getTocByUrl(url: String): TocItem?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToc(tocItem: TocItem): Long

    @Delete
    suspend fun deleteToc(tocItem: TocItem)


    @Query("SELECT * FROM spine WHERE bookId = :bookId AND tocId = :tocId")
    fun getSpine(bookId: Long, tocId: Long): Flow<List<SpineItem>>

    @Query("SELECT * FROM spine WHERE spineId = :id")
    suspend fun getSpineById(id : Long): SpineItem?

    @Query("SELECT * FROM spine WHERE location = :url")
    suspend fun getSpineByUrl(url : String): SpineItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpine(spineItem: SpineItem): Long

    @Delete
    suspend fun deleteSpine(spineItem: SpineItem)


}