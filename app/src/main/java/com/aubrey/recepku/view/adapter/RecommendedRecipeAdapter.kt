package com.aubrey.recepku.view.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.response.DataItems
import com.aubrey.recepku.databinding.ItemRecomendedRecipeBinding
import com.aubrey.recepku.view.detail.DetailActivity
import com.aubrey.recepku.view.home.HomeFragment
import com.bumptech.glide.Glide

class RecommendedRecipeAdapter(private val listRecommended: List<DataItems?>?, homeFragment: HomeFragment) : RecyclerView.Adapter<RecommendedRecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecomendedRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listRecommended?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendedRecipe = listRecommended?.get(position)
        recommendedRecipe?.let { nonNullRecipe ->
            holder.bind(nonNullRecipe)
        }
    }

    inner class ViewHolder(
        private val binding: ItemRecomendedRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendedRecipe: DataItems) {
            Glide.with(binding.root)
                .load(recommendedRecipe.photoUrl)
                .placeholder(R.drawable.menu)
                .error(R.drawable.menu)
                .into(binding.imgItemPhoto)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("recommendedRecipe", recommendedRecipe)
                Log.d("RecommendedRecipeAdapter", "Sending recommended recipe: $recommendedRecipe")
                binding.root.context.startActivity(intent)
            }
        }
    }

}