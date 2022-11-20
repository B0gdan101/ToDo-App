package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter (private val todos: MutableList<Todo>): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>()
{
    private val mainActivity = MainActivity()
    fun getTodos(): MutableList<Todo> {
        return todos
    }

    class TodoViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView)
    {}


    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean)
    {
        if (isChecked)
        {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size-1)
    }

    fun findCheckedTodo(): Todo? {
        return todos.find { todo ->
            todo.isChecked
        }
    }

    fun deleteCheckedTodo(){
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
              R.layout.item_todo,
              parent,
              false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = todos[position]
        holder.itemView.apply {
            tvTodoTitle.text = currentTodo.title
            cbDone.isChecked = currentTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, currentTodo.isChecked)
            cbDone.setOnCheckedChangeListener{ _, isChecked ->
                toggleStrikeThrough(tvTodoTitle, isChecked)
                currentTodo.isChecked = !currentTodo.isChecked
                if (findCheckedTodo() == null) {
                    MainActivity.Delete.deleteButton.hide()
                } else { MainActivity.Delete.deleteButton.show()}
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}
