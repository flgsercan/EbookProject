package com.example.zibook.feature_book.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.zibook.feature_book.presentation.details.DetailsScreen
import com.example.zibook.feature_book.presentation.home.HomeScreen
import com.example.zibook.feature_book.presentation.library.LibraryScreen
import com.example.zibook.feature_book.presentation.reader.ReaderScreen
import com.example.zibook.feature_book.presentation.settings.SettingsScreen
import com.example.zibook.feature_book.presentation.shared_components.BottomBar
import com.example.zibook.feature_book.presentation.shared_components.ConditionalBottomBar


@Composable
fun Navigation(navController: NavHostController) {

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        "home" -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true
        }
        "library" -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true
        }
        "setting" -> {
            // Show BottomBar and TopBar
            bottomBarState.value = true
        }
        "details?bookId={bookId}" -> {
            // Hide BottomBar and TopBar
            bottomBarState.value = false
        }
        "reader?chapterPath={chapterPath}" -> {
            bottomBarState.value = false
        }
    }

    Scaffold(
        bottomBar = {
            ConditionalBottomBar(
                navController = navController,
                bottomBarState = bottomBarState
            )
                    },
        content = { paddingValues ->

            NavHost(navController, startDestination = Screen.Home.route) {

                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.Library.route) {
                    LibraryScreen(navController)
                }
                composable(Screen.Setting.route) {
                    SettingsScreen()
                }
                composable(
                    Screen.Details.route + "?bookId={bookId}",
                    arguments = listOf(
                        navArgument(
                            name = "bookId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )
                ) {
                    DetailsScreen(navController)
                }
                composable(
                    Screen.Reader.route + "?chapterPath={chapterPath}",
                    arguments = listOf(
                        navArgument(
                            name = "chapterPath"
                        ) {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val path = it.arguments?.getString("chapterPath") ?: "/no/path/"
                    ReaderScreen(url = path)
                }
            }
            Spacer(modifier = Modifier.padding(paddingValues.calculateBottomPadding()))
        }
    )


}
