package com.example.zibook.feature_book.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "toc")
data class TocItem(
    val id: String,
    val label: String,
    val location: String,
    val bookId:Long,
    @PrimaryKey val tocId : Long? = null
)
