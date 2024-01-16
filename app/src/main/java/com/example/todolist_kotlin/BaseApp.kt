package com.example.todolist_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import java.io.File
import android.app.Application

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }
}