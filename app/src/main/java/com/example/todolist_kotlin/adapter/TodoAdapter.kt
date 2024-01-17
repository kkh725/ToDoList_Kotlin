package com.example.todolist_kotlin.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_kotlin.databinding.ListItemTodoBinding //listitemtodo xml 파일을 binding
import com.example.todolist_kotlin.model.TodoInfo


class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

private var lstTodo : ArrayList<TodoInfo> = ArrayList()

    init {
        /**
         * 이런식으로 직접 인스턴스를 생성해주는게 아닌 보통 데이터값을 받아와서 적용해준다.
         * main activity에서
         */
        val todoItem = TodoInfo()
        todoItem.todoContent = "코틀린 마스터하기"
        todoItem.todoDate = "20240116"
        lstTodo.add(todoItem) //동적리스트 타입이기때문에 add

        val todoItem2 = TodoInfo()
        todoItem2.todoContent = "헬스 마스터가 되어보기"
        todoItem2.todoDate = "20240116"
        lstTodo.add(todoItem2)

        val todoItem3 = TodoInfo()
        todoItem3.todoContent = "취업 성공하기"
        todoItem3.todoDate = "20240116"
        lstTodo.add(todoItem3)
    }

    /**
     * viewholder를 생성하는데 생성자로 binding 인스턴스를 받는다.
     * 그러니까 정리해보면, 사용자가 정의한 viewholder를 포함하고있는 recyclerview adapter를 만드는것. 어답터는 리사이클러뷰에 포함될것.
     * 뷰 홀더를 어답터 내부클래스로 정의하여 보기 편하게 만든다.
     * 이미 생성자로 List_Item_todo xml 이 참조되어있음. 따라서 binding으로 그냥 사용하기만하면됨.
     */

    class TodoViewHolder(private val binding : ListItemTodoBinding) : RecyclerView.ViewHolder(binding.root){ //recyclerview의 viewholder이 todoviewholder의 본질.
        fun bind(todoItem : TodoInfo){
            binding.tvContent.text = todoItem.todoContent
            binding.tvDate.text = todoItem.todoDate

            //리스트 삭제버튼 클릭연동
            binding.btnRemove.setOnClickListener {
                //쓰레기통 클릭시 내부 로직 구현.
                Log.d("hi","hi")
            }
        }
    }

    /**
     * 뷰홀더를 생성. 각 리스트 아이템 1개씩 구성될때마다 호출된다.
     * 각 리스트에 있는 아이템도 포함.
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ListItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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