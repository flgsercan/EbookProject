package com.example.zibook.feature_book.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.domain.model.NavItem


@Database(
    entities = [Book::class, NavItem::class],
    version = 1,
    exportSchema = false
)
abstract class BookDatabase: RoomDatabase() {

    abstract val bookDao: BookDao

    companion object {
        const val DATABASE_NAME = "ebook_db"
    }
}