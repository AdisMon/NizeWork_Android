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

        // Nuevos controles para expandir
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

        // 1. Datos Básicos
        holder.lblNombre.text = task.nombre
        holder.lblFecha.text = task.fechaL ?: "Sin fecha"
        holder.lblDesc.text = if (!task.descripcion.isNullOrEmpty()) task.descripcion else "Sin descripción detallada."

        // 2. LÓGICA DE EXPANDIR / CONTRAER
        val isExpanded = task.isExpanded
        holder.lblDesc.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.separator.visibility = if (isExpanded) View.VISIBLE else View.GONE

        // Cambiar icono de flecha (Arriba/Abajo)
        holder.btnExpand.setImageResource(
            if (isExpanded) android.R.drawable.arrow_up_float else android.R.drawable.arrow_down_float
        )

        // Click en la flecha
        holder.btnExpand.setOnClickListener {
            task.isExpanded = !task.isExpanded // Cambiamos el estado
            notifyItemChanged(position) // Refrescamos SOLO esta fila (animación suave)
        }

        // 3. LÓGICA DEL CHECKBOX
        updateCheckboxColor(holder.btnCompletar, task.isCompleted)

        holder.btnCompletar.setOnClickListener {
            task.isCompleted = !task.isCompleted // Invertimos estado
            updateCheckboxColor(holder.btnCompletar, task.isCompleted)

            // AQUÍ LUEGO LLAMAREMOS A LA API PARA GUARDAR EL CAMBIO
        }
    }

    private fun updateCheckboxColor(btn: ImageButton, isCompleted: Boolean) {
        if (isCompleted) {
            btn.setColorFilter(Color.parseColor("#4CAF50")) // Verde
        } else {
            btn.setColorFilter(Color.parseColor("#BDBDBD")) // Gris
        }
    }

    override fun getItemCount() = tasks.size

    fun updateList(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}