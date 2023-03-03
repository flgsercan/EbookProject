package com.example.zibook.feature_book.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
