package com.aubrey.recepku.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.ItemFavoriteBinding
import com.aubrey.recepku.databinding.ItemRecipeBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(private val favoriteList: List<DataItem?>?) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFavorite = favoriteList?.get(position)
        // Mengatur data resep ke tampilan item dalam RecyclerView
        currentFavorite?.let { nonNullFavorite ->
            holder.bind(nonNullFavorite)
        }
    }

    override fun getItemCount(): Int {
        return favoriteList?.size ?: 0
    }

    inner class ViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: DataItem) {
            Glide.with(binding.root)
                .load(recipe.photoUrl)
                .placeholder(R.drawable.menu)
                .error(R.drawable.menu)
                .into(binding.ivFavorite)
            binding.tvFavorite.text = recipe.title

//            binding.root.setOnClickListener {
//                recipeClickListener.onRecipeClicked(recipe)
//            }
        }
    }
}