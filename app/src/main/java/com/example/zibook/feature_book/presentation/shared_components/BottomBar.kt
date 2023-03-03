package com.example.zibook.feature_book.presentation.shared_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aregyan.compose.R
import com.example.zibook.feature_book.presentation.navigation.Screen


@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val bottomBarItems = listOf(
        Screen.Home,
        Screen.Library,
        Screen.Setting
    )


    Box {
        var width by remember { mutableStateOf(0f) }
        var currentIndex by remember { mutableStateOf(0) }
        val offsetAnim by animateFloatAsState(
            targetValue = when (currentIndex) {
                1 -> (width / 2f) - with(LocalDensity.current) { 50.dp.toPx() }
                2 -> width - with(LocalDensity.current){100.dp.toPx()}
                else -> 0f
            }
        )

        BottomNavigation(
            backgroundColor = colorResource(id = R.color.main_dark),
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned {
                    width = it.size.width.toFloat()
                },
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val iconColor = colorResource(id = R.color.main_light)
            bottomBarItems.forEachIndexed { index, item ->
                val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                AnimatedBottomNavigationItem(
                    selected = selected,
                    onClick = {
                        currentIndex = index
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = item.icon,
                    label = item.title,
                    selectedContentColor = iconColor
                )
            }
        }


        Box(
            modifier = Modifier
                .width(100.dp)
                .height(3.dp)
                .offset(with(LocalDensity.current) { offsetAnim.toDp() }, 0.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(colorResource(id = R.color.accent))
        )
    }


}


@Composable
fun AnimatedBottomNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: Int,
    label: String,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = LocalContentColor.current
) {

    val top by animateDpAsState(
        targetValue = if (selected) 0.dp else 56.dp,
        animationSpec = SpringSpec(dampingRatio = 0.5f, stiffness = 200f)
    )

    Box(
        modifier = Modifier
            .height(56.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick.invoke()
            }
            .padding(start = 30.dp, end = 30.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            tint = selectedContentColor,
            contentDescription = null,
            modifier = Modifier
                .height(56.dp)
                .width(26.dp)
                .offset(y = top)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(56.dp)
                .offset(y = top - 56.dp)
        ) {
            Text(
                text = label,
                color = selectedContentColor
            )
        }
    }
}

@Composable
fun ConditionalBottomBar(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomBar(navController = navController)
        } )
}

@Preview(showBackground = true)
@Composable
fun AnimatedBottomNavigationItemPreview() {
    val navController = rememberNavController()
    //BottomBar(navController)
}
