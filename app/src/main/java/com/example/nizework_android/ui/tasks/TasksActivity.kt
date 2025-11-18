package com.example.nizework_android.ui.tasks

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nizework_android.R

class TasksActivity : AppCompatActivity() {

    // Si esto falla, el error saldrá en el Toast
    private val viewModel: TasksViewModel by viewModels()
    private lateinit var adapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // 1. Intentamos cargar el diseño
            setContentView(R.layout.activity_tasks)

            // 2. Buscamos los componentes (Aquí suele fallar si el XML está mal)
            val recyclerView = findViewById<RecyclerView>(R.id.tasksRecyclerView)
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            val btnBack = findViewById<ImageButton>(R.id.backButton)

            // 3. Verificamos que existan (Si alguno es null, lanzamos el error nosotros mismos)
            if (recyclerView == null) throw Exception("No encuentro 'tasksRecyclerView' en activity_tasks.xml")
            if (progressBar == null) throw Exception("No encuentro 'progressBar' en activity_tasks.xml")
            // El botón back puede ser opcional, pero verifiquemos por si acaso
            // if (btnBack == null) throw Exception("No encuentro 'backButton'")

            // 4. Configuramos el adaptador
            adapter = TasksAdapter()
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

            if (btnBack != null) {
                btnBack.setOnClickListener { finish() }
            }

            // 5. Observamos los datos
            viewModel.tasks.observe(this) { taskList ->
                adapter.updateList(taskList)
            }

            viewModel.error.observe(this) { errorMsg ->
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            }

            viewModel.isLoading.observe(this) { isLoading ->
                if (progressBar != null) progressBar.isVisible = isLoading
            }

            // 6. Pedimos los datos
            viewModel.loadTasks()

        } catch (e: Exception) {
            // 🚨 AQUÍ ATRAPAMOS EL ERROR 🚨
            val mensajeError = "ERROR: ${e.message}"
            Toast.makeText(this, mensajeError, Toast.LENGTH_LONG).show()
            println(mensajeError) // También lo imprime en consola
        }
    }
}