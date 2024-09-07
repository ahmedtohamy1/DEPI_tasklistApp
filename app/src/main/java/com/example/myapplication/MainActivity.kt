package com.example.myapplication  // Ensure this is correct


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), TaskAdapter.OnTaskClickListener {

    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val addTaskButton: FloatingActionButton = findViewById(R.id.add_task_button)

        taskAdapter = TaskAdapter(taskList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        addTaskButton.setOnClickListener {
            showTaskDialog(null) // Open dialog for adding a new task
        }
    }

    // Show a dialog to add or edit a task
    private fun showTaskDialog(taskToEdit: Task?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_task, null)
        val taskTitleInput: EditText = dialogView.findViewById(R.id.task_title_input)
        val taskDescriptionInput: EditText = dialogView.findViewById(R.id.task_description_input)
        val taskDueDateInput: EditText = dialogView.findViewById(R.id.task_due_date_input)

        if (taskToEdit != null) {
            taskTitleInput.setText(taskToEdit.title)
            taskDescriptionInput.setText(taskToEdit.description)
            taskDueDateInput.setText(taskToEdit.dueDate)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle(if (taskToEdit != null) "Edit Task" else "Add Task")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val title = taskTitleInput.text.toString()
                val description = taskDescriptionInput.text.toString()
                val dueDate = taskDueDateInput.text.toString()

                if (title.isNotEmpty() && description.isNotEmpty() && dueDate.isNotEmpty()) {
                    if (taskToEdit != null) {
                        // Edit the task
                        taskToEdit.title = title
                        taskToEdit.description = description
                        taskToEdit.dueDate = dueDate
                        taskAdapter.notifyDataSetChanged()
                        Toast.makeText(this, "Task Updated!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Add a new task
                        val newTask = Task(title, description, dueDate)
                        taskList.add(newTask)
                        taskAdapter.notifyDataSetChanged()
                        Toast.makeText(this, "Task Added!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    // Handle task clicks for editing
    override fun onTaskClick(position: Int) {
        val task = taskList[position]
        showTaskDialog(task)
    }
}
