package com.aubrey.recepku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.databinding.ItemRecipeStepsBinding

class LowCalStepsAdapter (private val healthySteps: List<String>) :
    RecyclerView.Adapter<LowCalStepsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeStepsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val healthyIngredient = healthySteps[position]
        holder.bind(healthyIngredient)
    }

    override fun getItemCount(): Int {
        return healthySteps.size
    }

    inner class ViewHolder(private val binding: ItemRecipeStepsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(healthySteps: String) {
            binding.tvStepsValue.text = healthySteps
        }
    }
}