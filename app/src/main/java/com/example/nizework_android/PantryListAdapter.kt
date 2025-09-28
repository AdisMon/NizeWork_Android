package com.example.nizework_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PantryListAdapter(private val pantryItems: List<PantryItem>) :
    RecyclerView.Adapter<PantryListAdapter.PantryViewHolder>() {

    class PantryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.productNameTextView)
        val quantity: TextView = itemView.findViewById(R.id.quantityTextView)
        val completeButton: Button = itemView.findViewById(R.id.completeButton)
        val buyLaterButton: Button = itemView.findViewById(R.id.buyLaterButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PantryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pantry, parent, false)
        return PantryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pantryItems.size
    }

    override fun onBindViewHolder(holder: PantryViewHolder, position: Int) {
        val item = pantryItems[position]
        holder.productName.text = item.productName
        holder.quantity.text = item.quantity
        }
    }
