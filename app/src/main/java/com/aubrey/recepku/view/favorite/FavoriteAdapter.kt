package com.aubrey.recepku.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.databinding.ItemFavoriteBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(private val recipeClickListener: RecipeClickListener) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private val listFavorite = mutableListOf<FavoriteRecipe>()

    fun setFavoriteRecipes(favoriteRecipes: List<FavoriteRecipe>) {
        listFavorite.clear()
        listFavorite.addAll(favoriteRecipes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, recipeClickListener)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = listFavorite[position]
        holder.bind(favorite)
    }

    inner class ViewHolder(private val binding: ItemFavoriteBinding, private val recipeClickListener: RecipeClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteRecipe) {
            Glide.with(binding.root)
                .load(favorite.photoUrl)
                .placeholder(R.drawable.menu)
                .error(R.drawable.menu)
                .into(binding.ivFavorite)
            binding.tvFavorite.text = favorite.title

            binding.root.setOnClickListener {
                recipeClickListener.onRecipeClicked(favorite)
            }
        }
    }
}