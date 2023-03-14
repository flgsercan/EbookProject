package com.example.zibook.feature_book.presentation.reader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aregyan.compose.R

@Preview
@Composable
fun ReaderPreview() {
    var bottomBarState by remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable { bottomBarState = !bottomBarState }
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .background( color = Color.Cyan)
                ,
        )
        AnimatedVisibility(
            visible = bottomBarState,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.Yellow),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_arrow_back_ios_new_24
                        ),
                        contentDescription = "",
                        tint = Color.Red
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_arrow_back_ios_new_24
                        ),
                        contentDescription = "",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}