package com.aubrey.recepku.view.favorite

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.databinding.ItemFavoriteBinding
import com.aubrey.recepku.view.detail.DetailActivity
import com.bumptech.glide.Glide

class FavoriteAdapter() : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favoriteRecipes = mutableListOf<FavoriteRecipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val recipe = favoriteRecipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = favoriteRecipes.size

    fun setFavoriteRecipes(recipes: List<FavoriteRecipe>) {
        favoriteRecipes.clear()
        favoriteRecipes.addAll(recipes)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: FavoriteRecipe) {
            binding.tvFavorite.text = recipe.title
            Glide.with(binding.root)
                .load(recipe.photoUrl)
                .placeholder(R.drawable.menu)
                .error(R.drawable.menu)
                .into(binding.ivFavorite)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("favRecipe", recipe)
                Log.d("FavoriteAdapter", "Sending favorite recipe: $recipe")
                binding.root.context.startActivity(intent)
            }
        }
    }
}

