package com.example.zibook.feature_book.presentation.shared_components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aregyan.compose.R
import com.example.zibook.feature_book.domain.model.Book
import com.example.zibook.feature_book.presentation.navigation.Screen


@Composable

fun CardCell(navController: NavController,book: Book) {

    val cover = BitmapFactory.decodeFile(book.cover)
    val title = book.title
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(320.dp)
            .padding(15.dp)
            .clickable { navController.navigate(Screen.Details.route + "?bookId=${book.id}") },
        backgroundColor = colorResource(id = R.color.main_light),
        elevation = 0.dp
    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(0.dp)
        ) {
            Image(bitmap = cover.asImageBitmap(),contentDescription = "", modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
            )
            Text(
                text =  title,

                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.main_dark),
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CellPreview() {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(320.dp)
            .padding(15.dp)
            .clickable { },
        backgroundColor = colorResource(id = R.color.main_light),
        elevation = 0.dp
    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(0.dp)
        ) {
            val image: Painter = painterResource(id = R.drawable.cover2)
            Image(painter = image,contentDescription = "", modifier = Modifier.clip(RoundedCornerShape(5.dp)))
            Text(
                text =  "title",

                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.main_dark),
                fontSize = 15.sp
            )
        }
    }
}
