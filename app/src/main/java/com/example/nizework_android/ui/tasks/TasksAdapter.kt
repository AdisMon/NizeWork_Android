package com.example.nizework_android.ui.tasks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nizework_android.R
import com.example.nizework_android.data.model.Task

class TasksAdapter(private var tasks: List<Task> = emptyList()) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lblNombre: TextView = view.findViewById(R.id.taskNameTextView)
        val lblDesc: TextView = view.findViewById(R.id.taskDescTextView)
        val lblFecha: TextView = view.findViewById(R.id.dueDateTextView)
        val btnCompletar: ImageButton = view.findViewById(R.id.completeButton)
        val btnExpand: ImageButton = view.findViewById(R.id.btnExpand)
        val separator: View = view.findViewById(R.id.separator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.lblNombre.text = task.nombre
        holder.lblFecha.text = task.fechaL ?: "Sin fecha"
        holder.lblDesc.text = task.descripcion

        val isExpanded = task.isExpanded
        holder.lblDesc.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.separator.visibility = if (isExpanded) View.VISIBLE else View.GONE

        holder.btnExpand.setImageResource(
            if (isExpanded) android.R.drawable.arrow_up_float else android.R.drawable.arrow_down_float
        )

        holder.btnExpand.setOnClickListener {
            task.isExpanded = !task.isExpanded
            notifyItemChanged(position)
        }

        updateCheckboxColor(holder.btnCompletar, task.isCompleted)

        holder.btnCompletar.setOnClickListener {
            task.isCompleted = !task.isCompleted
            updateCheckboxColor(holder.btnCompletar, task.isCompleted)
        }
    }

    private fun updateCheckboxColor(btn: ImageButton, isCompleted: Boolean) {
        if (isCompleted) {
            btn.setColorFilter(Color.parseColor("#4CAF50"))
        } else {
            btn.setColorFilter(Color.parseColor("#BDBDBD"))
        }
    }

    override fun getItemCount() = tasks.size
    fun updateList(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}