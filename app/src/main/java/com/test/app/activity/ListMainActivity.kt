package com.test.app.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.test.app.adapter.TodoAdapter
import com.test.app.database.TodoDatabase
import com.test.app.model.TodoInfo
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import test.ToDoList_app.databinding.ActivityListMainBinding
import test.ToDoList_app.databinding.DialogEditBinding
import java.text.SimpleDateFormat
import java.util.Date

class ListMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListMainBinding
    private lateinit var todoAdapter: TodoAdapter
    lateinit var dark_background: View
    private lateinit var roomDatabase: TodoDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMainBinding.inflate(layoutInflater)
        dark_background = binding.view3
        onWindowFocusChanged(true)

        setContentView(binding.root)

        //광고 sdk 초기화
        MobileAds.initialize(this) {}

        //하단베너 광고 로드
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        //어댑터 인스턴스 생성
        todoAdapter = TodoAdapter(dark_background)

        //리사이클러뷰의 set adapter .
        binding.rvTodo.adapter = todoAdapter

        //room db 초기화
        roomDatabase = TodoDatabase.getInstance(applicationContext)!!

        //전체 데이터 load 코루틴 사용. 비동기작업 처리.
        CoroutineScope(Dispatchers.IO).launch {
            //비동기 작업 로직 작성.
            //데이터베이스에 있는 데이터들을 읽어서, TodoInfo에 넣어주고 그 값들을 todoadapter에 넣어준다. 그리고 화면에 표시할것
            val lstTodo = roomDatabase.todoDao().getAllReadData() as ArrayList<TodoInfo>
            for(todoItem in lstTodo){
                todoAdapter.addListItem(todoItem)
            }
            runOnUiThread {
                todoAdapter.notifyDataSetChanged() //ui 조작이기때문에 dispatchers.io는 ㄴㄴ
            }

        }


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

                    val todoItem = TodoInfo()
                    todoItem.todoContent = bindingDialog.etMemo.text.toString()
                    todoItem.todoDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Date())
                    todoAdapter.addListItem(todoItem)

                    //코루틴, 그리고 runOnUIThread. 비동기작업처리
                    CoroutineScope(Dispatchers.IO).launch{
                        roomDatabase.todoDao().insertTodoData(todoItem) //데이터베이스에도 삽입
                        runOnUiThread {
                            todoAdapter.notifyDataSetChanged() //리스트 새로고침 로직. 데이터는 반영되지만 ui상에서는 구현안됨. 이 문장이 있어야 어답터/뷰홀더가 새로고침됨.
                        }
                    }



                }) .setNegativeButton("취소",DialogInterface.OnClickListener { dialogInterface, which ->
                    dark_background.visibility  = View.INVISIBLE
                    //알아서 취소버튼 눌려짐.
                })
                .setOnDismissListener { dark_background.visibility = View.INVISIBLE }
                .show()
        }

    }

    //전체 풀스크린으로 사용하기. 홈,뒤로가기,메뉴 등 안보이게
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val decorView: View = window.decorView
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}