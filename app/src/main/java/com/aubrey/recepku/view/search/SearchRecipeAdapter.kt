package com.aubrey.recepku.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.ItemRecipeSearchBinding
import com.bumptech.glide.Glide

class SearchRecipeAdapter(private val recipes: List<DataItem?>?) :
    RecyclerView.Adapter<SearchRecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecipeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes?.get(position)
        recipe?.let { holder.bind(recipe) }

        holder.binding.cardRecipe.setOnClickListener {
//            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
//            intent.putExtra(DetailActivity.EXTRA_RECIPE_ID, recipe.id)
//            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return recipes?.size ?: 0
    }

    inner class ViewHolder(val binding: ItemRecipeSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: DataItem) {
            binding.tvRecipe.text = recipe.title
            binding.tvRecipeDescription.text = recipe.description

            Glide.with(itemView)
                .load(recipe.photoUrl)
                .into(binding.imgRecipe)
        }
    }
}