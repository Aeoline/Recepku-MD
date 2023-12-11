package com.aubrey.recepku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.databinding.ItemIngredientsItemBinding

class IngredientsAdapter(private val ingredients: List<String>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngredientsItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }

    inner class ViewHolder(private val binding: ItemIngredientsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: String) {
            binding.tvIngredientsValue.text = ingredient
        }
    }
}