package com.example.zibook.feature_book.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spine")
data class SpineItem(
    val location: String? = null,
    val bookId:Int? = null,
    @PrimaryKey val spineId : Int? = null
)
