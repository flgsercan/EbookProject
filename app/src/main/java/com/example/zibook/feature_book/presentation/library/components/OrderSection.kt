package com.example.zibook.feature_book.presentation.library.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.zibook.feature_book.domain.util.BookOrder
import com.example.zibook.feature_book.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    bookOrder: BookOrder = BookOrder.Title(OrderType.Descending),
    onOrderChange: (BookOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = bookOrder is BookOrder.Title,
                onSelect = { onOrderChange(BookOrder.Title(bookOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = bookOrder is BookOrder.Author,
                onSelect = { onOrderChange(BookOrder.Author(bookOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = bookOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(bookOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = bookOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(bookOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}