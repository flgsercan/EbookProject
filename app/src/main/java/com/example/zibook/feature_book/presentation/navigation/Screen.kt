package com.example.zibook.feature_book.presentation.navigation

import com.aregyan.compose.R


sealed class Screen(var route:String, var icon:Int, var title:String){
    object Home: Screen("home", R.drawable.home_icon,"Home")
    object Library: Screen("library", R.drawable.library_icon,"Library")
    object Setting: Screen("setting", R.drawable.settings_icon,"Setting")
    object Details: Screen("details", R.drawable.ic_eternity_dark, "Details")
    object Reader: Screen("reader", R.drawable.ic_eternity_dark, "Reader")
}
