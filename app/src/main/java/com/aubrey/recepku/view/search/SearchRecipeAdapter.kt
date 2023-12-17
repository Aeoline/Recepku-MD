package com.aubrey.recepku.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.ItemRecipeSearchBinding
import com.bumptech.glide.Glide


class SearchRecipeAdapter: ListAdapter<DataItem, SearchRecipeAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRecipeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder( private val binding: ItemRecipeSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: DataItem) {
            binding.tvRecipe.text = recipe.title
//            val targetMuscles = recipe.
//            val muscleNames = targetMuscles?.joinToString(", ") { it?.targetMuscleName ?: "" }
            Glide.with(binding.root)
                .load(recipe.photoUrl)
                .into(binding.imgRecipe)

            itemView.setOnClickListener {
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