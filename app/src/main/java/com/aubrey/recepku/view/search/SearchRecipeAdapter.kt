package com.aubrey.recepku.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.ItemRecipeSearchBinding
import com.aubrey.recepku.view.search.RecipeClickListener
import com.bumptech.glide.Glide


class SearchRecipeAdapter(private val recipeClickListener: RecipeClickListener): ListAdapter<DataItem, SearchRecipeAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRecipeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, recipeClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder( private val binding: ItemRecipeSearchBinding, private val recipeClickListener: RecipeClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: DataItem) {
            binding.tvRecipe.text = recipe.title
            binding.tvRecipeDescription.text = recipe.description
            Glide.with(binding.root)
                .load(recipe.photoUrl)
                .into(binding.imgRecipe)

            itemView.setOnClickListener {
                binding.root.setOnClickListener {
                    recipeClickListener.onRecipeClicked(recipe)
                }
            }
        }
    }



    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}