package com.example.zibook

import android.app.Application
import android.content.Context
import androidx.compose.runtime.movableContentOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EbookApp: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: EbookApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = applicationContext()
    }
}
