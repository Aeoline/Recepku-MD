package com.aubrey.recepku.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aubrey.recepku.R
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.ActivityDetailBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.adapter.IngredientsAdapter
import com.aubrey.recepku.view.adapter.LowCalIngredientsAdapter
import com.aubrey.recepku.view.adapter.LowCalStepsAdapter
import com.aubrey.recepku.view.adapter.StepsAdapter
import com.aubrey.recepku.view.favorite.FavoriteViewModel
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var recipe: DataItem? = null
    private var favoriteRecipe: FavoriteRecipe? = null


    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from intent
        recipe = intent.getParcelableExtra("recipe")
        favoriteRecipe = intent.getParcelableExtra("favRecipe")

        // Log retrieved data
        Log.d("DetailActivity", "recipe: $recipe, favoriteRecipe: $favoriteRecipe")

        // Check if both recipe and favoriteRecipe are null
        if (recipe == null && favoriteRecipe == null) {
            Log.e("DetailActivity", "No recipe data found in intent")
            finish() // Close the activity if no valid data is found
            return
        }

        toLowCal()
        setAdapter()
        setupData()
        toggleFavorite()
    }

    private fun toLowCal() {
        val lowCalButton = binding.switchButton
        val switchText = binding.tvSwitch
        var isLowCal = false
        lowCalButton.setOnClickListener {
            if (!isLowCal) {
                lowCalButton.backgroundTintList = resources.getColorStateList(R.color.white)
                lowCalButton.strokeColor = ContextCompat.getColor(this, R.color.dark_green)
                switchText.setTextColor(ContextCompat.getColor(this, R.color.dark_green))
                switchText.text = "Switch to Normal Calories"
                binding.rvIngredients.adapter = recipe?.healthyIngredients?.let { LowCalIngredientsAdapter(it) }
                    ?: favoriteRecipe?.healthyIngredients?.let { LowCalIngredientsAdapter(it) }
                binding.rvSteps.adapter = recipe?.healthySteps?.let { LowCalStepsAdapter(it) }
                    ?: favoriteRecipe?.healthySteps?.let { LowCalStepsAdapter(it) }
                binding.tvCalories.text = recipe?.healthyCalories?.toString()
                    ?: favoriteRecipe?.healthyCalories?.toString()
                isLowCal = true
            } else {
                binding.rvIngredients.adapter = recipe?.ingredients?.let { IngredientsAdapter(it) }
                    ?: favoriteRecipe?.ingredients?.let { IngredientsAdapter(it) }
                binding.rvSteps.adapter = recipe?.steps?.let { StepsAdapter(it) }
                    ?: favoriteRecipe?.steps?.let { StepsAdapter(it) }
                binding.tvCalories.text = recipe?.calories?.toString()
                    ?: favoriteRecipe?.calories?.toString()
                lowCalButton.backgroundTintList = resources.getColorStateList(R.color.dark_green)
                lowCalButton.strokeColor = ContextCompat.getColor(this, com.google.android.material.R.color.mtrl_btn_transparent_bg_color)
                switchText.setTextColor(ContextCompat.getColor(this, R.color.white))
                switchText.text = "Switch to Lower Calories"
                isLowCal = false
            }
        }

    }

    private fun toggleFavorite() {
        val favoriteButton = binding.ibFavorite

        // Update the button state based on the recipe's isFavorite flag
        if (recipe?.isFavorite == true || favoriteRecipe?.isFavorite == true) {
            favoriteButton.setImageResource(R.drawable.ic_favorite)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
        }

        // Set the click listener to toggle the favorite state
        favoriteButton.setOnClickListener {
            recipe?.isFavorite = !recipe?.isFavorite!!
            favoriteRecipe?.isFavorite = !favoriteRecipe?.isFavorite!!

            // Update the button state
            if (recipe?.isFavorite == true || favoriteRecipe?.isFavorite == true) {
                favoriteButton.setImageResource(R.drawable.ic_favorite)
            } else {
                favoriteButton.setImageResource(R.drawable.ic_favorite_border)
            }

            // Save or delete the recipe from the favorites list
            if (recipe?.isFavorite == true || favoriteRecipe?.isFavorite == true) {
                viewModel.insert(
                    FavoriteRecipe(
                        recipe?.id,
                        recipe?.title,
                        recipe?.description,
                        recipe?.photoUrl,
                        recipe?.ingredients,
                        recipe?.steps,
                        recipe?.healthyIngredients,
                        recipe?.healthySteps,
                        recipe?.calories,
                        recipe?.healthyCalories,
                        recipe?.isFavorite!!
                    )
                )
            } else {
                viewModel.delete(
                    FavoriteRecipe(
                        recipe?.id,
                        recipe?.title,
                        recipe?.description,
                        recipe?.photoUrl,
                        recipe?.ingredients,
                        recipe?.steps,
                        recipe?.healthyIngredients,
                        recipe?.healthySteps,
                        recipe?.calories,
                        recipe?.healthyCalories,
                        recipe?.isFavorite!!
                    )
                )
            }
        }
    }

    private fun setAdapter() {
        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        binding.rvIngredients.adapter = recipe?.ingredients?.let { IngredientsAdapter(it.filterNotNull()) }
            ?: favoriteRecipe?.ingredients?.let { IngredientsAdapter(it) }

        binding.rvSteps.layoutManager = LinearLayoutManager(this)
        binding.rvSteps.adapter = recipe?.steps?.let { StepsAdapter(it.filterNotNull()) }
            ?: favoriteRecipe?.steps?.let { StepsAdapter(it) }

        val favoriteButton = binding.ibFavorite

        if (recipe?.id == favoriteRecipe?.id && favoriteRecipe?.isFavorite == true) {
            favoriteButton.setImageResource(R.drawable.ic_favorite)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun setupData() {

        recipe?.let { item ->
            binding.tvRecipeName.text = item.title
            binding.tvDescription.text = item.description
            binding.tvCalories.text = item.calories.toString()
            Glide.with(binding.ivRecipe)
                .load(item.photoUrl)
                .into(binding.ivRecipe)
        } ?: favoriteRecipe?.let { recipe ->
            binding.tvRecipeName.text = recipe.title
            binding.tvDescription.text = recipe.description
            binding.tvCalories.text = recipe.calories.toString()
            Glide.with(binding.ivRecipe)
                .load(recipe.photoUrl)
                .into(binding.ivRecipe)
        }
    }


}