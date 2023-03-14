package com.example.zibook.feature_book.presentation.details

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.zibook.feature_book.presentation.navigation.Screen
import com.maximillianleonov.blurimage.BlurImage
import com.aregyan.compose.R

@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val toc = state.tocItemList





    if(state.book != null) {

        val cover = state.book.cover
        val bookId = state.book.id
        val title = state.book.title
        val author = state.book.author

        BlurImage(data =  cover,
            contentDescription ="",
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            blurRadius = 20
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 10f,
                        endY = 600f
                    )
                )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                                contentDescription = ""
                            )
                        }
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                                contentDescription = ""
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))

                    AsyncImage(model = ImageRequest.Builder(
                        LocalContext.current)
                        .data(cover)
                        .placeholder(R.drawable.cover2)
                        .build() , contentDescription ="Cover",
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp)))

                    Text(
                        text =  title,

                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFB3B3B3),
                        fontSize = 20.sp
                    )
                    Text(
                        text = author,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFB3B3B3),
                        fontSize = 15.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column() {

                        }
                        Column() {

                        }
                        Column() {

                        }
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text(
                            text = "Continue Reading",
                            color = Color(0xFFB3B3B3)
                        )
                    }
                }
            }
            if (toc != null) {
                items(toc) { model ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                navController.navigate(
                                    Screen.Reader.route + "?bookId=${bookId}&chapterUrl=${model.location}"
                                )
                            },
                        text = model.label!!,
                        color = Color(0xFFB3B3B3),
                        fontSize = 15.sp
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.padding(50.dp))
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.accent),
                            Color.Black
                        ),
                        startY = 10f,
                        endY = 600f
                    )
                )
        )
    }
    
}
