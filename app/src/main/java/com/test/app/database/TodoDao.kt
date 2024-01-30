package com.test.app.database

import androidx.annotation.RequiresPermission.Read
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.test.app.model.TodoInfo

@Dao
interface TodoDao {
    @Insert // 쿼리문 select 문이 내재되어있는듯? 데이터삽임
    fun insertTodoData(todoInfo: TodoInfo) // 구현은 가져다쓸때

    @Read

    @Update
    fun updateTodoData(todoInfo: TodoInfo)

    @Delete
    fun deleteTodoData(todoInfo: TodoInfo)

    //데이터베이스의 전체데이터 조회. tododate가 가장 최신인 순으로 정렬해서 가지고온다.
    @Query("SELECT * FROM TodoInfo ORDER BY todoDate")
    fun getAllReadData() : List<TodoInfo>
}