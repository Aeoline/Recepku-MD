package com.aubrey.recepku.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.databinding.ItemRecipeBinding
import com.aubrey.recepku.databinding.ItemRecomendedRecipeBinding
import com.aubrey.recepku.view.home.HomeFragment
import com.bumptech.glide.Glide

class RecommendedRecipeAdapter (private val recipeClickListener: HomeFragment): ListAdapter<Recommended, RecommendedRecipeAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecomendedRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendedRecipe = getItem(position)
        holder.bind(recommendedRecipe)
    }

    inner class ViewHolder(private val binding: ItemRecomendedRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendedRecipe: Recommended) {
            val recommended = recommendedRecipe.recommended
            Glide.with(binding.root)
                .load(recommended.photoUrl)
                .placeholder(R.drawable.menu)
                .error(R.drawable.menu)
                .into(binding.imgItemPhoto)

            binding.root.setOnClickListener {
                recipeClickListener.onRecipeClicked(recommendedRecipe)
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recommended>() {
            override fun areItemsTheSame(oldItem: Recommended, newItem: Recommended): Boolean {
                return oldItem.recommended.id == newItem.recommended.id
            }

            override fun areContentsTheSame(oldItem: Recommended, newItem: Recommended): Boolean {
                return oldItem == newItem
            }
        }
    }
}