package com.example.zibook.feature_book.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import com.example.zibook.MainDark
import com.example.zibook.MainLight
import com.example.zibook.feature_book.presentation.details.DetailsScreen
import com.example.zibook.feature_book.presentation.home.HomeScreen
import com.example.zibook.feature_book.presentation.library.LibraryScreen
import com.example.zibook.feature_book.presentation.reader.ReaderScreen
import com.example.zibook.feature_book.presentation.settings.SettingsScreen
import com.example.zibook.feature_book.presentation.shared_components.BottomBar
import com.example.zibook.feature_book.presentation.shared_components.ConditionalBottomBar
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController) {

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {

        systemUiController.setStatusBarColor(
            color = MainLight,
            darkIcons = useDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = MainDark,
            darkIcons = false
        )

        onDispose {}
    }

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
        "reader?bookId={bookId}&chapterUrl={chapterUrl}" -> {
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

            AnimatedNavHost(
                navController,
                startDestination = Screen.Home.route
            ) {
                composable(
                    Screen.Home.route,
                    enterTransition = {
                       when(initialState.destination.route) {
                           Screen.Setting.route -> {
                               slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                                   animationSpec = tween(700))
                           }
                           Screen.Library.route -> {
                               slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                                   animationSpec = tween(700))
                           }
                           Screen.Details.route -> {
                               slideIntoContainer(AnimatedContentScope.SlideDirection.Up,
                                   animationSpec = tween(700))
                           }
                           else -> null
                       }
                    },
                exitTransition = {
                    when(targetState.destination.route) {
                        Screen.Setting.route -> {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,
                                animationSpec = tween(700))
                        }
                        Screen.Library.route -> {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,
                                animationSpec = tween(700))
                        }
                        Screen.Details.route -> {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Down,
                                animationSpec = tween(700))
                        }
                        else -> null
                    }
                }
                ) {
                    HomeScreen()
                }
                composable(
                    Screen.Library.route,
                    enterTransition = {
                        when(initialState.destination.route) {
                            Screen.Home.route -> {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                                    animationSpec = tween(700))
                            }
                            Screen.Setting.route -> {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Right,
                                    animationSpec = tween(700))
                            }
                            Screen.Details.route -> {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(700))
                            }
                            else -> null
                        }
                    },
                    exitTransition = {
                        when(targetState.destination.route) {
                            Screen.Home.route -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                                    animationSpec = tween(700))
                            }
                            Screen.Setting.route -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Left,
                                    animationSpec = tween(700))
                            }
                            Screen.Details.route -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down,
                                    animationSpec = tween(700))
                            }
                            else -> null
                        }
                    }
                ) {
                    LibraryScreen(navController)
                }
                composable(
                    Screen.Setting.route,
                    enterTransition = {
                        when(initialState.destination.route) {
                            Screen.Home.route -> {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                                    animationSpec = tween(700))
                            }
                            Screen.Library.route -> {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Left,
                                    animationSpec = tween(700))
                            }
                            else -> null
                        }
                    },
                    exitTransition = {
                        when(targetState.destination.route) {
                            Screen.Home.route -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                                    animationSpec = tween(700))
                            }
                            Screen.Library.route -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Right,
                                    animationSpec = tween(700))
                            }
                            else -> null
                        }
                    }
                ) {
                    SettingsScreen()
                }
                composable(
                    Screen.Details.route + "?bookId={bookId}",
                    enterTransition = {
                        when(initialState.destination.route) {
                            Screen.Home.route -> {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Down,
                                    animationSpec = tween(700))
                            }
                            Screen.Library.route -> {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Down,
                                    animationSpec = tween(700))
                            }
                            Screen.Reader.route -> {
                                slideIntoContainer(AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(700))
                            }
                            else -> null
                        }
                    },
                    exitTransition = {
                        when(targetState.destination.route) {
                            Screen.Home.route -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(700))
                            }
                            Screen.Library.route -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(700))
                            }
                            Screen.Reader.route -> {
                                slideOutOfContainer(AnimatedContentScope.SlideDirection.Down,
                                    animationSpec = tween(700))
                            }
                            else -> null
                        }
                    },
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
                    Screen.Reader.route + "?bookId={bookId}&chapterUrl={chapterUrl}",
                    enterTransition = {
                        slideIntoContainer(AnimatedContentScope.SlideDirection.Down,
                            animationSpec = tween(700))
                    },
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentScope.SlideDirection.Up,
                            animationSpec = tween(700))
                    },
                    arguments = listOf(
                        navArgument(
                            name = "bookId"
                        ) {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                        navArgument(
                            name = "chapterUrl"
                        ) {
                            type = NavType.StringType
                            defaultValue = "nopath"
                        }
                    )
                ) {
                    ReaderScreen()
                }
            }
            Spacer(modifier = Modifier.padding(paddingValues.calculateBottomPadding()))
        }
    )


}
