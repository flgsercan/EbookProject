package com.example.zibook.feature_book.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.SpineItem
import com.example.zibook.feature_book.domain.model.TocItem


@Database(
    entities = [Book::class, TocItem::class, SpineItem::class],
    version = 1,
    exportSchema = false
)
abstract class BookDatabase: RoomDatabase() {

    abstract val bookDao: BookDao

    companion object {
        const val DATABASE_NAME = "ebook_db"
    }
}