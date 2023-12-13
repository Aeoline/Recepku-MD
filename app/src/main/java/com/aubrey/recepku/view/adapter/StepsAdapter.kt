package com.aubrey.recepku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.databinding.ItemRecipeStepsBinding

class StepsAdapter(private val steps: List<String?>) :
    RecyclerView.Adapter<StepsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeStepsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val steps = steps[position]
        if (steps != null) {
            holder.bind(steps)
        }
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    inner class ViewHolder(private val binding: ItemRecipeStepsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(steps: String) {
            binding.tvStepsValue.text = steps
        }
    }
}