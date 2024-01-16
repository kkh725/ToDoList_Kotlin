package com.example.todolist_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist_kotlin.adapter.TodoAdapter
import com.example.todolist_kotlin.databinding.ActivityListMainBinding
import com.example.todolist_kotlin.model.TodoInfo

class ListMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMainBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        todoAdapter = TodoAdapter()
        //리사이클러뷰의 setadapter .
        binding.rvTodo.adapter = todoAdapter

    }
}