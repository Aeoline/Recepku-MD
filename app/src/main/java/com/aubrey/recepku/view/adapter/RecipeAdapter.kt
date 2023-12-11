package com.aubrey.recepku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.model.recipe.Favorite
import com.aubrey.recepku.databinding.ItemRecipeBinding
import com.aubrey.recepku.view.home.HomeFragment
import com.bumptech.glide.Glide

class RecipeAdapter(private val recipeClickListener: HomeFragment) :
    ListAdapter<Favorite, RecipeAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteRecipe = getItem(position)
        holder.bind(favoriteRecipe)
    }

    inner class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteRecipe: Favorite) {
            val recipe = favoriteRecipe.recipe
            Glide.with(binding.root)
                .load(recipe.photoUrl)
                .placeholder(R.drawable.menu)
                .error(R.drawable.menu)
                .into(binding.foodImage)
            binding.tvRecipeName.text = recipe.title

            binding.root.setOnClickListener {
                recipeClickListener.onRecipeClicked(favoriteRecipe)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favorite>() {
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.recipe.id == newItem.recipe.id
            }

            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
        }
    }
}