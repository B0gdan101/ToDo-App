package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    object Delete{
        lateinit var deleteButton : FloatingActionButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf())
        Delete.deleteButton = findViewById(R.id.fabDelete)

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        fabAdd.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater : LayoutInflater = layoutInflater
            val dialogLayout : View = inflater.inflate(R.layout.dialog_box,null)

            with(builder)
            {
                setCancelable(true)
                setView(dialogLayout)
            }
            val alertDialog = builder.create()
            alertDialog.show()

            val submit = dialogLayout.findViewById<Button>(R.id.btnAddItem)
            val editText = dialogLayout.findViewById<EditText>(R.id.etAddItem)
            submit.setOnClickListener {
                val toDoText = editText.text.toString()
                if (toDoText.isNotEmpty()){
                    val toDo = Todo(toDoText)
                    todoAdapter.addTodo(toDo)
                    editText.text.clear()
                    alertDialog.dismiss()
                } else {
                    Toast.makeText(this, "No Task Entered", Toast.LENGTH_SHORT).show()
                    Log.w("submit_button","No Task Entered")
                }
            }
        }

        if (todoAdapter.findCheckedTodo() == null) {
            Delete.deleteButton.hide()
        } else { Delete.deleteButton.show() }

        fabDelete.setOnClickListener {
            if (todoAdapter.getTodos().isNotEmpty()) {
                todoAdapter.deleteCheckedTodo()
                Delete.deleteButton.hide()}
            else
                Toast.makeText(this, "No tasks to delete", Toast.LENGTH_SHORT).show()
        }

    }
}