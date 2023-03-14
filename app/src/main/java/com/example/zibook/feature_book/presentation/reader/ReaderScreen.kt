package com.example.zibook.feature_book.presentation.reader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.aregyan.compose.R
import com.example.zibook.MainDark
import com.example.zibook.MainLight

@Composable
fun ReaderScreen(viewModel: ReaderViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    val data = state.data

    var bottomBarState by remember {
        mutableStateOf(true)
    }

   Surface(
       modifier = Modifier.fillMaxSize(),
       color = MainLight,
       contentColor = MainDark
   ) {
       Scaffold(
           bottomBar = {
               ReaderBottomBar(
                   bottomBarState,
                   viewModel = viewModel,
                   modifier = Modifier
                       .background(color = colorResource(id = R.color.main_dark))
                       .fillMaxWidth()
                       .fillMaxHeight(0.09f)
               )
           },
           content = { paddingValues ->
               Column(
                   modifier = Modifier
                       .background(MainLight)
                       .fillMaxSize()
                       .padding(0.dp)
               ) {
                   if (data != null) {
                       LazyColumn(
                           modifier = Modifier
                               .fillMaxWidth()
                               .fillMaxHeight()
                               .padding(5.dp)
                               .noRippleClickable {
                                   bottomBarState = !bottomBarState
                               }
                               .fillMaxHeight(0.91f)
                               .fillMaxWidth(0.95f)
                               .align(CenterHorizontally)
                       ) {
                           item {
                               data.title?.let {
                                   Text(
                                       text = it,
                                       fontSize = 25.sp,
                                       color = MainDark
                                   )
                               }
                           }

                           items(data.body.size) {
                               when {
                                   data.body[it] == "\n" -> Spacer(modifier = Modifier.padding(0.3.dp))

                                   data.body[it] == "\n\n" -> Spacer(modifier = Modifier.padding(0.8.dp))

                                   data.body[it].startsWith("//image=") -> {
                                       AsyncImage(model = ImageRequest.Builder(
                                           LocalContext.current)
                                           .data(state.book!!.opfPath.removeSuffix("content.opf") + data.body[it].removePrefix("//image=../"))
                                           .placeholder(R.drawable.photo)
                                           .build() , contentDescription ="image content",
                                           modifier = Modifier
                                               .fillMaxWidth())
                                       Text(state.book.opfPath.removeSuffix("content.opf") + data.body[it].removePrefix("//image=../"))
                                   }

                                   else -> Text(
                                       text = data.body[it],
                                       fontSize = 20.sp,
                                       color = MainDark
                                   )
                               }
                           }
                       }
                   } else {
                       Column(
                           modifier = Modifier.fillMaxSize(),
                           verticalArrangement = Arrangement.Center,
                           horizontalAlignment = CenterHorizontally
                       ) {
                           Text(text = "state.currentChapterId?.let { state.chapters?.get(it)?.location }.toString()")
                       }
                   }

               }
               Spacer(modifier = Modifier.padding(paddingValues.calculateBottomPadding()))
           }
       )
   }

}

inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember {MutableInteractionSource()}
    ) {
        onClick()
    }
}

@Composable
fun ReaderBottomBar(
    bottomBarState: Boolean = true,
    viewModel: ReaderViewModel,
    modifier: Modifier
) {
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.onEvent(ReaderEvent.PreviousChapter) }) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.baseline_arrow_back_ios_new_24
                    ),
                    contentDescription = "",
                    tint = colorResource(id = R.color.main_light)
                )
            }
            IconButton(onClick = { viewModel.onEvent(ReaderEvent.NextChapter) }) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.baseline_arrow_forward_ios_24
                    ),
                    contentDescription = "",
                    tint = colorResource(id = R.color.main_light)
                )
            }
        }
    }
}
