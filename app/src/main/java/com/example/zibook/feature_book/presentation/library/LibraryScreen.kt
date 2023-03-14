package com.example.zibook.feature_book.presentation.library


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aregyan.compose.R
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.presentation.navigation.Screen


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
            horizontalAlignment = CenterHorizontally
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

        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            val itemCount = if (state.books.size % 2 == 0) {
                state.books.size / 2
            } else {
                state.books.size / 2 + 1
            }
            items(itemCount) {
                if(it >= itemCount - 2 && !state.endReached && !state.isLoading) {
                        viewModel.onEvent(LibraryEvent.LoadBooksPaginated)
                }
                LibraryRow(rowIndex = it, entries = state.books, navController = navController)
            }
        }

    }

}

@Composable
fun LibraryRow(rowIndex: Int, entries: List<Book>, navController: NavController) {
    
    Column {
        Row {
            LibraryEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if(entries.size >= rowIndex * 2 + 2) {
                LibraryEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun LibraryEntry(entry: Book, navController: NavController, modifier: Modifier) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1/2f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    Screen.Details.route + "?bookId=${entry.id}"
                )
            }
    ) {
        Column {
            AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data(entry.cover)
                .placeholder(R.drawable.photo)
                .build() , contentDescription =entry.title + " cover",
                modifier = Modifier
                    .aspectRatio(1/2f)
                    .fillMaxSize()
                    .align(CenterHorizontally))
            Text(
                text = entry.title,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}