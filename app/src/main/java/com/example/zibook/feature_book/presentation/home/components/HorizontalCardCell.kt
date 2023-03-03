package com.example.zibook.feature_book.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aregyan.compose.R

@Composable

fun HorizontalCardCell(cover: Int, title: String, author: String) {
    val shape = RoundedCornerShape(30.dp)
    Card(
        modifier = Modifier
            .width(336.dp)
            .height(189.dp)
            .shadow(
                elevation = 10.dp,
                shape = shape
            )
            .background(
                color = Color(0xFFFFFFFF),
                shape = shape
            )
            .clip(RoundedCornerShape(30.dp))
            .clickable {}
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            val image: Painter = painterResource(id = cover)
            Image(painter = image,contentDescription = "", modifier = Modifier.clip(RoundedCornerShape(25.dp)))
            Spacer(modifier = Modifier.width(1.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Column() {
                    Spacer(modifier = Modifier
                        .height(15.dp)
                        )
                    Text(
                        text =  title,

                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorResource(id = R.color.main_dark),
                        fontSize = 20.sp
                    )
                    Text(
                        text = author,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFB3B3B3),
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier
                    .height(45.dp)
                    )
                Text(
                    text = "Completed",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFB3B3B3),
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}

@Preview(showBackground = false)
@Composable
fun HCellPreview() {
    HorizontalCardCell(R.drawable.cover2, "Mass Effect", "Bioware")
}