package com.aubrey.recepku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.databinding.ItemIngredientsItemBinding

class LowCalIngredientsAdapter (private val healthyIngredients: List<String?>) :
    RecyclerView.Adapter<LowCalIngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredientsItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val healthyIngredients = healthyIngredients[position]
        if (healthyIngredients != null) {
            holder.bind(healthyIngredients)
        }
    }

    override fun getItemCount(): Int {
        return healthyIngredients.size
    }

    inner class ViewHolder(private val binding: ItemIngredientsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(healthyIngredients: String) {
            binding.tvIngredientsValue.text = healthyIngredients
        }
    }
}