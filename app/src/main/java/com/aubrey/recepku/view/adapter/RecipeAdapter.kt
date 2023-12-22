package com.aubrey.recepku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.ItemRecipeBinding
import com.aubrey.recepku.view.home.RecipeClickListener
import com.bumptech.glide.Glide

class RecipeAdapter(val listRecipe: List<DataItem?>?, private val recipeClickListener: RecipeClickListener) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, recipeClickListener)
    }

    override fun getItemCount(): Int {
        return listRecipe?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = listRecipe?.get(position)
        recipe?.let { nonNullRecipe ->
            holder.bind(nonNullRecipe)
        }
    }

    class ViewHolder(private val binding: ItemRecipeBinding, private val recipeClickListener: RecipeClickListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: DataItem) {
            Glide.with(binding.root)
                .load(recipe.photoUrl)
                .placeholder(R.drawable.menu)
                .error(R.drawable.menu)
                .into(binding.foodImage)
            binding.tvRecipeName.text = recipe.title

            binding.root.setOnClickListener {
                recipeClickListener.onRecipeClicked(recipe)
            }
        }
    }
}