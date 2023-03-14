package com.example.zibook.feature_book.data.data_source

import androidx.room.*
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.model.TocItem
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book WHERE id > :id ORDER BY id ASC LIMIT :limit")
    fun getBooks(limit: Int, id: Int): Flow<List<Book>>


    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBookById(id : Int): Book?

    @Query("SELECT * FROM book WHERE path = :path")
    suspend fun getBookByPath(path : String): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)



    @Query("SELECT * FROM toc WHERE bookId = :bookId")
    fun getToc(bookId: Int): Flow<List<TocItem>>

    @Query("SELECT * FROM toc WHERE id = :id")
    suspend fun getTocById(id : Int): TocItem?
    @Query("SELECT * FROM toc WHERE location = :url")
    suspend fun getTocByUrl(url: String): TocItem?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToc(tocItem: TocItem)

    @Delete
    suspend fun deleteToc(tocItem: TocItem)


    @Query("SELECT * FROM spine WHERE bookId = :bookId")
    fun getSpine(bookId: Int): Flow<List<SpineItem>>

    @Query("SELECT * FROM spine WHERE spineId = :id")
    suspend fun getSpineById(id : Int): SpineItem?

    @Query("SELECT * FROM spine WHERE location = :url")
    suspend fun getSpineByUrl(url : String): SpineItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpine(spineItem: SpineItem)

    @Delete
    suspend fun deleteSpine(spineItem: SpineItem)


}