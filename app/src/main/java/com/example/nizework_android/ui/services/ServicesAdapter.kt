package com.example.nizework_android.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nizework_android.R
import com.example.nizework_android.data.model.ServicioItem

class ServicesAdapter(
    private val services: List<ServicioItem>,
    private val onItemClicked: (ServicioItem) -> Unit
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

        holder.categoryTextView.text = service.categoria
        holder.serviceTypeTextView.text = service.nombre
        holder.serviceNameTextView.text = service.empresa
        holder.dueDateTextView.text = service.fecha

        val nombreNormal = service.nombre.lowercase().trim()
        val icono = when {
            nombreNormal.contains("luz") || nombreNormal.contains("cfe") -> R.drawable.cfelogo
            nombreNormal.contains("agua") || nombreNormal.contains("sideapa") -> R.drawable.sideapalogo
            nombreNormal.contains("cable") || nombreNormal.contains("mega") -> R.drawable.megacablelogo
            nombreNormal.contains("spot") -> R.drawable.spotifylogo
            nombreNormal.contains("telmex") -> R.drawable.telmexlogo
            nombreNormal.contains("telcel") -> R.drawable.telcellogo
            else -> R.drawable.ic_launcher_foreground
        }
        holder.logoImageView.setImageResource(icono)

        holder.itemView.setOnClickListener {
            onItemClicked(service)
        }
    }
}