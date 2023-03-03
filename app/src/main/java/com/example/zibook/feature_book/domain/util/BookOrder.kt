package com.example.zibook.feature_book.domain.util

sealed class BookOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): BookOrder(orderType)
    class Author(orderType: OrderType): BookOrder(orderType)

    fun copy(orderType: OrderType): BookOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Author -> Author(orderType)
        }
    }
}
