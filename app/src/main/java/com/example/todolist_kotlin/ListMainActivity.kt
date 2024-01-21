package com.example.todolist_kotlin

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.todolist_kotlin.adapter.TodoAdapter
import com.example.todolist_kotlin.databinding.ActivityListMainBinding
import com.example.todolist_kotlin.databinding.DialogEditBinding
import com.example.todolist_kotlin.model.TodoInfo
import java.text.SimpleDateFormat
import java.util.Date

class ListMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMainBinding
    private lateinit var todoAdapter: TodoAdapter
    lateinit var dark_background: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMainBinding.inflate(layoutInflater)
        dark_background = binding.view3



        setContentView(binding.root)

        //어댑터 인스턴스 생성
        todoAdapter = TodoAdapter(dark_background)

        //리사이클러뷰의 set adapter .
        binding.rvTodo.adapter = todoAdapter

        //작성하기 버튼 클릭
        binding.btnWrite.setOnClickListener{
            /**
             * 작성하기 버튼을 눌렀을때
             * 여기서 alertdialog 를 띄워서 작성값 입력받기
             */
            dark_background.visibility  = View.VISIBLE

            val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context),binding.root,false)
            // 어둡게 하는 배경 레이아웃 설정

            AlertDialog.Builder(this)
                .setView(bindingDialog.root)
                .setPositiveButton("작성완료",DialogInterface.OnClickListener { dialogInterface, which ->
                    //작성완료 버튼 눌렀을때.
                    dark_background.visibility  = View.INVISIBLE
                    val todoItem = TodoInfo()
                    todoItem.todoContent = bindingDialog.etMemo.text.toString()
                    todoItem.todoDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Date())
                    todoAdapter.addListItem(todoItem)
                    todoAdapter.notifyDataSetChanged() //리스트 새로고침 로직. 데이터는 반영되지만 ui상에서는 구현안됨. 이 문장이 있어야 어답터/뷰홀더가 새로고침됨.

                }) .setNegativeButton("취소",DialogInterface.OnClickListener { dialogInterface, which ->
                    dark_background.visibility  = View.INVISIBLE
                    //알아서 취소버튼 눌려짐.
                })
                .show()
        }

    }
}