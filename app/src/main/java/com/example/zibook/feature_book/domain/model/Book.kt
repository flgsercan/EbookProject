package com.example.zibook.feature_book.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class Book (
    val cover : String,
    val author : String,
    val title : String,
    val path : String,
    val opfPath: String,
    @PrimaryKey val id : Int? = null

)
