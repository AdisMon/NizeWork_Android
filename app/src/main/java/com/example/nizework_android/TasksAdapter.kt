package com.example.nizework_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskNameTextView)
        val dueDate: TextView = itemView.findViewById(R.id.dueDateTextView)
        val completeButton: ImageButton = itemView.findViewById(R.id.completeButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.taskName
        holder.dueDate.text = task.dueDate

        holder.completeButton.setOnClickListener {
            Toast.makeText(holder.itemView.context, "${task.taskName} completada!", Toast.LENGTH_SHORT).show()
            // Aquí podrías añadir lógica para eliminar la tarea de la lista o marcarla visualmente
        }
    }
}