package com.example.nizework_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// --- INICIO DE LA MODIFICACIÓN ---
// Añadimos un nuevo parámetro al constructor: una función que se ejecutará al hacer clic.
class ServicesAdapter(
    private val services: List<Service>,
    private val onItemClicked: (Service) -> Unit // Esta es la acción de clic
) : RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val logoImageView: ImageView = itemView.findViewById(R.id.logoImageView)
        val serviceTypeTextView: TextView = itemView.findViewById(R.id.serviceTypeTextView)
        val serviceNameTextView: TextView = itemView.findViewById(R.id.serviceNameTextView)
        val dueDateTextView: TextView = itemView.findViewById(R.id.dueDateTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service_card, parent, false)
        return ServiceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return services.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.categoryTextView.text = service.category
        holder.logoImageView.setImageResource(service.logoResId)
        holder.serviceTypeTextView.text = service.serviceType
        holder.serviceNameTextView.text = service.serviceName
        holder.dueDateTextView.text = service.dueDate

        // Le decimos a la tarjeta entera (itemView) que al ser presionada,
        // ejecute la función 'onItemClicked' que recibimos.
        holder.itemView.setOnClickListener {
            onItemClicked(service)
        }
    }
    // --- FIN DE LA MODIFICACIÓN ---
}