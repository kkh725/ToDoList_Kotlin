package com.example.todolist_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist_kotlin.databinding.ActivityMainBinding
import com.example.todolist_kotlin.ListMainActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}