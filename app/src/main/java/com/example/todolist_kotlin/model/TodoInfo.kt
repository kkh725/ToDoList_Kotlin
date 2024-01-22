package com.example.todolist_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TodoInfo (){

    var todoContent : String = "" // 메모 내용
    var todoDate : String = "" // 메모 일자

    @PrimaryKey(autoGenerate = true) //auto로 개인키가 만들어지고 id값은 1씩 증가한다
    var id : Int = 0
}