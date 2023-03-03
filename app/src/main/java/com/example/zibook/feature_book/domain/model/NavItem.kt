package com.example.zibook.feature_book.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "toc")
data class NavItem(
    val id: String? = null,
    val label: String? = null,
    val location: String? = null,
    val bookId:Int? = null,
    @PrimaryKey val NavId : Int? = null
)
