package com.example.learningdatastore

import android.app.Application
import com.example.learningdatastore.data.AppContainer
import com.example.learningdatastore.data.DefaultAppContainer

class LearningDataStoreApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = this)
    }
}