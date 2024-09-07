package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val tasks: List<Task>, private val listener: OnTaskClickListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    interface OnTaskClickListener {
        fun onTaskClick(position: Int)
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.task_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.task_description)
        val dueDateTextView: TextView = itemView.findViewById(R.id.task_due_date)

        init {
            itemView.setOnClickListener {
                listener.onTaskClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTextView.text = task.title
        holder.descriptionTextView.text = task.description
        holder.dueDateTextView.text = task.dueDate
    }

    override fun getItemCount() = tasks.size
}
