package com.example.todolist_kotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.todolist_kotlin.model.TodoInfo

@Database(entities = [TodoInfo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    /**
     * 보통 이런식으로 싱글톤으로 데이터베이스를 생성한다.
     * 이런 형태는 자주 사용됨.
     * 최초에 한번 할당되고, 메모리가 이미 할당된 상태이기 때문에 만들때마다 추가로 생성하지않아도된다.
     * 최초로 한번만 생성해주면된다. getinstance 를 한번만 호출하면됨.
     */
    abstract  fun todoDao() : TodoDao

    //싱글톤 오브젝트
    companion object{
        private var instance : TodoDatabase? = null
        @Synchronized //여러곳에서 동시호출 -> 호출순서대로
        fun getInstance(context : Context) : TodoDatabase? {
            if (instance == null) {
                synchronized(TodoDatabase::class) {
                    instance = databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo-database"
                    ).build()
                }
            }
            return instance
        }
    }

}