package com.example.zibook.feature_book.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zibook.feature_book.presentation.home.components.HorizontalCardCell
import com.aregyan.compose.R


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.main_light))
            .wrapContentSize(Alignment.Center)
    ) {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = listOf(
                    "Mass Effect",
                    "Effect",
                    "Mass Effect",
                    "Dragon Age"
                )
            ) {
                cell ->
                HorizontalCardCell(cover = R.drawable.cover2, title = cell , author = "Bioware")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun BooksScreenPreview() {
    HomeScreen()
}