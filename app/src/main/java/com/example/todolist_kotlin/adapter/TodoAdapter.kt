package com.example.todolist_kotlin.adapter

import android.app.Activity
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import com.example.todolist_kotlin.database.TodoDao
import com.example.todolist_kotlin.database.TodoDatabase
import com.example.todolist_kotlin.databinding.DialogEditBinding
import com.example.todolist_kotlin.databinding.ListItemTodoBinding //listitemtodo xml 파일을 binding
import com.example.todolist_kotlin.model.TodoInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


class TodoAdapter(private val darkBackground: View) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

private var lstTodo : ArrayList<TodoInfo> = ArrayList()
    private lateinit var roomDatabase: TodoDatabase

    fun addListItem(todoItem: TodoInfo){ //todoinfo 객체를 하나 넣어줘야함.
        lstTodo.add(0,todoItem) //얘는 항상 마지막에 들어오도록 되어있는데, 스택형식으로 넣어주면 좋을듯? 이렇게 해주면 항상 0번째 index로 들어오게된다.
    }

    /**
     * viewholder를 생성하는데 생성자로 binding 인스턴스를 받는다.
     * 그러니까 정리해보면, 사용자가 정의한 viewholder를 포함하고있는 recyclerview adapter를 만드는것. 어답터는 리사이클러뷰에 포함될것.
     * 뷰 홀더를 어답터 내부클래스로 정의하여 보기 편하게 만든다.
     * 이미 생성자로 List_Item_todo xml 이 참조되어있음. 따라서 binding으로 그냥 사용하기만하면됨.
     */

    //inner 클래스로 정의해줄시에 부모 클래스의 private 에 접근가능하다.
    inner class TodoViewHolder(private val binding : ListItemTodoBinding) : RecyclerView.ViewHolder(binding.root){ //recyclerview의 viewholder이 todoviewholder의 본질.
        fun bind(todoItem : TodoInfo){
            binding.tvContent.text = todoItem.todoContent
            binding.tvDate.text = todoItem.todoDate

            //리스트 삭제버튼 클릭연동
            binding.btnRemove.setOnClickListener {
                //쓰레기통 클릭시 내부 로직 구현.
                //onBindViewHolder에서 todoItem을 bind
                darkBackground.visibility = VISIBLE

                AlertDialog.Builder(binding.root.context)
                    .setTitle("주의")
                    .setMessage("제거하시면 데이터는 복구되지 않습니다!\n")
                    .setPositiveButton("제거",DialogInterface.OnClickListener { dialog, which ->
                        darkBackground.visibility = INVISIBLE



                        //코루틴을 통한 데이터베이스 값 삭제
                        CoroutineScope(Dispatchers.IO).launch {
                            val innerLstTodo = roomDatabase.todoDao().getAllReadData() //이 값을 어댑터 생성시에 받아와도 좋을듯?
                            for (item in innerLstTodo){
                                if(item.todoContent == todoItem.todoContent && item.todoDate == todoItem.todoDate)
                                roomDatabase.todoDao().deleteTodoData(item)
                            }

                            lstTodo.remove(todoItem)
                            (binding.root.context as Activity).runOnUiThread {
                                notifyDataSetChanged()
                                Toast.makeText(binding.root.context,"제거되었습니다.", LENGTH_SHORT).show()
                            } //runonuithread는 액티비티상에서만 사용가능함.

                        }

                    })
                    .setNegativeButton("취소",DialogInterface.OnClickListener { dialog, which ->
                        darkBackground.visibility = INVISIBLE
                    })
                    .setOnDismissListener { darkBackground.visibility = INVISIBLE }
                    .show()


            }

            binding.root.setOnClickListener {  // list_item_todo의 모든영역 의미
                val bindingDialog = DialogEditBinding.inflate(LayoutInflater.from(binding.root.context),binding.root,false)
                //기존작성 데이터보여주기
                bindingDialog.etMemo.setText(todoItem.todoContent)
                // 어둡게 하는 배경 레이아웃 설정
                darkBackground.visibility = VISIBLE

                AlertDialog.Builder(binding.root.context) //내가 binding하고있는 화면의 메인context. 지금 리스트가 listmainactivity에 올라갈거니까.
                    .setView(bindingDialog.root)
                    .setPositiveButton("작성완료",DialogInterface.OnClickListener { dialogInterface, which ->
                        CoroutineScope(Dispatchers.IO).launch {

                            val innerLstTodo = roomDatabase.todoDao().getAllReadData()
                            for (item in innerLstTodo){
                                if(item.todoContent == todoItem.todoContent && item.todoDate == todoItem.todoDate)
                                    item.todoContent = bindingDialog.etMemo.text.toString()
                                item.todoDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Date())
                                roomDatabase.todoDao().updateTodoData(item)
                            }

                            //ui modify
                            todoItem.todoContent = bindingDialog.etMemo.text.toString()
                            todoItem.todoDate = SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Date())
                            darkBackground.visibility = INVISIBLE
                            // arraylist 수정
                            lstTodo.set(adapterPosition,todoItem)

                            (binding.root.context as Activity).runOnUiThread { notifyDataSetChanged() } //runonuithread는 액티비티상에서만 사용가능함.
                        }


                    }) .setNegativeButton("취소",DialogInterface.OnClickListener { dialogInterface, which ->
                        //알아서 취소버튼 눌려짐.
                        darkBackground.visibility = INVISIBLE
                    })
                    .setOnDismissListener { darkBackground.visibility = INVISIBLE }
                    .show()
            }
        }
    }

    /**
     * 뷰홀더를 생성. 각 리스트 아이템 1개씩 구성될때마다 호출된다.
     * 각 리스트에 있는 아이템도 포함.
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ListItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //room db 초기화
        roomDatabase = TodoDatabase.getInstance(binding.root.context)!!
        return TodoViewHolder(binding)
    }

    /**
     * 뷰홀더가 바인딩이 이루어질때 해줘야 될 처리.
     * onCreateViewHolder으로 만들어진 뷰 홀더가 여기로 넘어온다고 생각.
     * Position은 내가 선택한 리스트의 position을 의미한다. = index
     */

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(lstTodo[position]) //홀더에 내가만들었던 리스트아이템중에 내가 누른것의 정보를 bind.
    }

    override fun getItemCount(): Int {
        return lstTodo.size
    }


}