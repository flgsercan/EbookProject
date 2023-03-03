package com.example.zibook.feature_book.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.zibook.*
import com.example.zibook.feature_book.presentation.home.HomeScreen
import com.example.zibook.feature_book.presentation.navigation.Navigation
import com.example.zibook.feature_book.presentation.shared_components.BottomBar
import com.example.zibook.feature_book.presentation.shared_components.ComposablePermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setContent {
            MainScreen(this.applicationContext)
        }

    }






    private fun requestPermissions() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET
            )
        )
    }
    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.entries.forEach {
            Log.e("DEBUG", "${it.key} = ${it.value}")
        }
    }

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context) {
    val navController = rememberNavController()
    ComposablePermission(Manifest.permission.READ_EXTERNAL_STORAGE, onDenied = {}, onGranted = {})
    Navigation(navController)
}




@Preview(showBackground = true)
@Composable
fun BooksScreenPreview() {
    HomeScreen()
}


