package com.example.zibook.feature_book.presentation.library


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aregyan.compose.R
import com.example.zibook.feature_book.presentation.library.components.OrderSection
import com.example.zibook.feature_book.presentation.shared_components.CardCell


@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    if (state.books.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                viewModel.onEvent(LibraryEvent.ScanForBooks)
            }
            ) {
                Text(
                    text = "Scan for books"
                )
            }
        }
    } else {
        LazyVerticalGrid( columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(1.dp, 1.dp, 1.dp,1.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.main_light))
        ) {
            item(span = { 
                GridItemSpan(maxLineSpan)
            }) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    bookOrder = state.bookOrder,
                    onOrderChange = {
                        viewModel.onEvent(LibraryEvent.Order(it))
                    }
                )
            }
            items(state.books.size) { i ->
                CardCell(navController, state.books[i])
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LibraryPreview() {
    //LibraryScreen()
}