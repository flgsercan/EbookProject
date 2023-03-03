package com.example.zibook.feature_book.presentation.details

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.zibook.feature_book.domain.model.NavigationItemModel
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
    val toc = state.navItemList
    val cover =  if (state.book != null)
        BitmapFactory.decodeFile(state.book.cover)
    else
        BitmapFactory.decodeFile("/storage/emulated/0/Testappdir/The Author's POV/cover.png")

    val title = if (state.book != null) state.book.title else "Title"
    val author = if (state.book != null) state.book.author else "Author"
    val chapterPath = if (state.book != null)
        "/storage/emulated/0/Testappdir/"  + state.book.title + "/" + state.book.opfPath.removeSuffix("content.opf")
    else
        "/no/path/"



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

                    Image(
                        bitmap = cover.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                    )

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
                            .height(25.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.Reader.route +
                                            "?chapterPath=${
                                                chapterPath +
                                                        if (
                                                            model.location!!
                                                                .contains("../")
                                                        )
                                                            model.location.removePrefix("../")
                                                        else
                                                            model.location
                                            }"
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

    
}

@Preview (showBackground = true)
@Composable
fun DetailPreview() {

    val image: Painter = painterResource(id = R.drawable.cover2)
    BlurImage(data =  R.drawable.cover2,
        contentDescription ="",
        modifier = Modifier
            .fillMaxSize(),
        contentScale = ContentScale.FillHeight,
        blurRadius = 10
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
                    startY = 50f,
                    endY = 1100f
                )
            )
    )
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
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
        }
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
        )

            Text(
                text =  "Title",

                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFB3B3B3),
                fontSize = 20.sp
            )


            Text(
                text = "Author",
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
                text = "404",
                color = Color(0xFFB3B3B3)
                )
        }
        val toc = listOf<String>("agu","bugu","hede", "hödö")
        LazyColumn() {
            items(toc) { model ->
                 Text(
                     text = model,
                     color = Color(0xFFB3B3B3)
                 )
            }
        }
    }
}

