package com.aubrey.recepku.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aubrey.recepku.R
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.data.response.DataItems
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
    private var recommendedRecipe: DataItems? = null


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
        recommendedRecipe = intent.getParcelableExtra("recommendedRecipe")

        // Log retrieved data
        Log.d("DetailActivity", "recipe: $recipe, favoriteRecipe: $favoriteRecipe, recommendedRecipe: $recommendedRecipe")

        // Check if both recipe and favoriteRecipe are null
        if (recipe == null && favoriteRecipe == null && recommendedRecipe == null) {
            Log.e("DetailActivity", "No recipe data found in intent")
            finish() // Close the activity if no valid data is found
            return
        }

        toLowCal()
        setAdapter()
        setupData()
        toggleFavorite()
//        updateFavoriteIcon(recipeId)
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
                    ?: favoriteRecipe?.healthyIngredients?.let { LowCalIngredientsAdapter(it) } ?: recommendedRecipe?.healthyIngredients?.let { LowCalIngredientsAdapter(it.filterNotNull()) }
                binding.rvSteps.adapter = recipe?.healthySteps?.let { LowCalStepsAdapter(it) }
                    ?: favoriteRecipe?.healthySteps?.let { LowCalStepsAdapter(it) } ?: recommendedRecipe?.healthySteps?.let { LowCalStepsAdapter(it.filterNotNull()) }
                binding.tvCalories.text = recipe?.healthyCalories?.toString()
                    ?: favoriteRecipe?.healthyCalories?.toString() ?: recommendedRecipe?.healthyCalories?.toString()
                isLowCal = true
            } else {
                binding.rvIngredients.adapter = recipe?.ingredients?.let { IngredientsAdapter(it) }
                    ?: favoriteRecipe?.ingredients?.let { IngredientsAdapter(it) } ?: recommendedRecipe?.ingredients?.let { IngredientsAdapter(it.filterNotNull()) }
                binding.rvSteps.adapter = recipe?.steps?.let { StepsAdapter(it) }
                    ?: favoriteRecipe?.steps?.let { StepsAdapter(it) } ?: recommendedRecipe?.steps?.let { StepsAdapter(it.filterNotNull()) }
                binding.tvCalories.text = recipe?.calories?.toString()
                    ?: favoriteRecipe?.calories?.toString() ?: recommendedRecipe?.calories?.toString()
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

        // Determine which recipe is not null and create FavoriteRecipe
        val favoriteRecipe = when {
            recipe != null -> {
                FavoriteRecipe(
                    recipe!!.id,
                    recipe!!.title,
                    recipe!!.description,
                    recipe!!.photoUrl,
                    recipe!!.ingredients,
                    recipe!!.steps,
                    recipe!!.healthyIngredients,
                    recipe!!.healthySteps,
                    recipe!!.calories,
                    recipe!!.healthyCalories,
                    recipe!!.isFavorite
                )
            }
            favoriteRecipe != null -> {
                FavoriteRecipe(
                    favoriteRecipe!!.id,
                    favoriteRecipe!!.title,
                    favoriteRecipe!!.description,
                    favoriteRecipe!!.photoUrl,
                    favoriteRecipe!!.ingredients,
                    favoriteRecipe!!.steps,
                    favoriteRecipe!!.healthyIngredients,
                    favoriteRecipe!!.healthySteps,
                    favoriteRecipe!!.calories,
                    favoriteRecipe!!.healthyCalories,
                    favoriteRecipe!!.isFavorite
                )
            }
            recommendedRecipe != null -> {
                recommendedRecipe!!.isFavorite?.let {
                    FavoriteRecipe(
                        recommendedRecipe!!.id,
                        recommendedRecipe!!.title,
                        recommendedRecipe!!.description,
                        recommendedRecipe!!.photoUrl,
                        recommendedRecipe!!.ingredients,
                        recommendedRecipe!!.steps,
                        recommendedRecipe!!.healthyIngredients,
                        recommendedRecipe!!.healthySteps,
                        recommendedRecipe!!.calories,
                        recommendedRecipe!!.healthyCalories,
                        it
                    )
                }
            }
            else -> {
                Log.e("DetailActivity", "No valid recipe data found")
                return
            }
        }

        // Check if the recipe is already in the database and update isFavorite accordingly
        if (favoriteRecipe != null) {
            viewModel.isFavorite(favoriteRecipe.id).observe(this) { isFavoriteInDb ->
                var isFavorite = isFavoriteInDb == true
                updateFavoriteButton(isFavorite)

                favoriteButton.setOnClickListener {
                    if (isFavorite) {
                        viewModel.delete(favoriteRecipe)
                        favoriteRecipe.isFavorite = false // Set to false when deleted
                        updateFavoriteButton(false)
                        isFavorite = false
                    } else {
                        viewModel.insert(favoriteRecipe)
                        favoriteRecipe.isFavorite = true // Set to true when added
                        updateFavoriteButton(true)
                        isFavorite = true
                    }
                }
            }
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val favoriteButton = binding.ibFavorite
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
        }
    }



    private fun setAdapter() {
        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        binding.rvIngredients.adapter = recipe?.ingredients?.let { IngredientsAdapter(it.filterNotNull()) }
            ?: favoriteRecipe?.ingredients?.let { IngredientsAdapter(it) }
                    ?: recommendedRecipe?.ingredients?.let { IngredientsAdapter(it.filterNotNull()) }

        binding.rvSteps.layoutManager = LinearLayoutManager(this)
        binding.rvSteps.adapter = recipe?.steps?.let { StepsAdapter(it.filterNotNull()) }
            ?: favoriteRecipe?.steps?.let { StepsAdapter(it) }
            ?: recommendedRecipe?.steps?.let { StepsAdapter(it.filterNotNull()) }

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
        } ?: recommendedRecipe?.let { recipe ->
            binding.tvRecipeName.text = recipe.title
            binding.tvDescription.text = recipe.description
            binding.tvCalories.text = recipe.calories.toString()
            Glide.with(binding.ivRecipe)
                .load(recipe.photoUrl)
                .into(binding.ivRecipe)
        }


    }


}