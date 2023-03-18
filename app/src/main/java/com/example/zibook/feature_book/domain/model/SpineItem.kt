package com.example.zibook.feature_book.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spine")
data class SpineItem(
    val location: String,
    val bookId: Long,
    val tocId: Long? = null,
    @PrimaryKey val spineId : Long? = null
)
