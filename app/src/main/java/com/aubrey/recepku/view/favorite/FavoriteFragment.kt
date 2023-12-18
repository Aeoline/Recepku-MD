package com.aubrey.recepku.view.favorite

import com.aubrey.recepku.view.favorite.FavoriteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.FragmentFavoriteBinding
import com.aubrey.recepku.databinding.ItemFavoriteBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.adapter.IngredientsAdapter
import com.aubrey.recepku.view.adapter.LowCalIngredientsAdapter
import com.aubrey.recepku.view.adapter.LowCalStepsAdapter
import com.aubrey.recepku.view.adapter.StepsAdapter
import com.bumptech.glide.Glide

interface RecipeClickListener {
    fun onRecipeClicked(recipe: FavoriteRecipe)
}

interface RecommendedRecipeClickListener {
    fun onRecipeClicked(recipe: Recommended)
}

class FavoriteFragment : Fragment(), RecipeClickListener, RecommendedRecipeClickListener {
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var rvFavorite: RecyclerView
    private lateinit var binding: ItemFavoriteBinding



    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        rvFavorite = binding.rvFavorite
        // Set layout manager untuk RecyclerView
        rvFavorite.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi adapter kosong
        favoriteAdapter = FavoriteAdapter(this)
        // Atur adapter ke RecyclerView
        rvFavorite.adapter = favoriteAdapter

        // Observasi data favorit dari ViewModel
        viewModel.getAllFavoriteRecipe().observe(viewLifecycleOwner) { favoriteRecipes ->
            favoriteAdapter.setFavoriteRecipes(favoriteRecipes)
        }

        return view
    }


    override fun onRecipeClicked(recipe: FavoriteRecipe) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.item_card_detail, null)

//      Adapter

        // Adapter
        val stepsAdapter = StepsAdapter(recipe.steps ?: emptyList())
        val ingredientsAdapter = IngredientsAdapter(recipe.ingredients ?: emptyList())
        val lowCalStepsAdapter = LowCalStepsAdapter(recipe.healthySteps ?: emptyList())
        val lowCalIngAdapter = LowCalIngredientsAdapter(recipe.healthyIngredients ?: emptyList())

//        ui
        val ivRecipe = dialogView.findViewById<ImageView>(R.id.foodImage)
        val tvRecipeName = dialogView.findViewById<TextView>(R.id.tvRecipeName)
        val tvRecipeDescription = dialogView.findViewById<TextView>(R.id.tv_description)
        val tvCalories = dialogView.findViewById<TextView>(R.id.tv_calories_value)
        val rvIngredients = dialogView.findViewById<RecyclerView>(R.id.rv_ingredients)
        val rvSteps = dialogView.findViewById<RecyclerView>(R.id.rv_steps)
        val backBtn = dialogView.findViewById<ImageButton>(R.id.btn_back_detail)
        val favBtn = dialogView.findViewById<ImageButton>(R.id.btn_favorite_detail)
        val lowcalBtn = dialogView.findViewById<ImageButton>(R.id.btn_lowcal)

//        condition
        var isLowcal = false

//        setup
        Glide.with(ivRecipe)
            .load(recipe.photoUrl)
            .into(ivRecipe)
        tvRecipeName.text = recipe.title
        tvRecipeDescription.text = recipe.description
        favBtn.setImageResource(R.drawable.ic_favorite_fill)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        val stepsLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvIngredients.layoutManager = layoutManager
        rvIngredients.adapter = ingredientsAdapter

        rvSteps.adapter = stepsAdapter
        rvSteps.layoutManager = stepsLayoutManager

        tvCalories.text = recipe.calories.toString()



        val alertDialog = dialogBuilder.setView(dialogView).create()

        lowcalBtn.setOnClickListener {
            if (isLowcal) {
                lowcalBtn.setImageResource(R.drawable.ic_food)
                isLowcal = false
                rvIngredients.adapter = ingredientsAdapter
                tvCalories.text = recipe.calories.toString()
                rvSteps.adapter = stepsAdapter

            } else {
                lowcalBtn.setImageResource(R.drawable.ic_food_healthy)
                isLowcal = true
                rvIngredients.adapter = lowCalIngAdapter
                tvCalories.text = recipe.healthyCalories.toString()
                rvSteps.adapter = lowCalStepsAdapter
            }
        }

        backBtn.setOnClickListener {
            alertDialog.dismiss()
        }

        favBtn.setOnClickListener {
            viewModel.delete(
                FavoriteRecipe(
                    recipe.id,
                    recipe.title,
                    recipe.description,
                    recipe.photoUrl,
                    recipe.ingredients,
                    recipe.steps,
                    recipe.healthyIngredients,
                    recipe.healthySteps,
                    recipe.calories,
                    recipe.healthyCalories,
                )
            )
            alertDialog.dismiss()
        }


        alertDialog.show()
    }

    override fun onRecipeClicked(recipe: Recommended) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.item_card_detail, null)


        val ingredientsAdapter = recipe.recommended.ingredients?.let { IngredientsAdapter(it) }
        val stepsAdapter = recipe.recommended.steps?.let { StepsAdapter(it) }
        val lowCalIngAdapter = recipe.recommended.healthyIngredients?.let { LowCalIngredientsAdapter(it) }
        val lowCalStepsAdapter = recipe.recommended.healthySteps?.let { LowCalStepsAdapter(it) }

        val backBtn = dialogView.findViewById<ImageButton>(R.id.btn_back_detail)
        val favBtn = dialogView.findViewById<ImageButton>(R.id.btn_favorite_detail)
        val lowcalBtn = dialogView.findViewById<ImageButton>(R.id.btn_lowcal)

        val ivRecipe = dialogView.findViewById<ImageView>(R.id.foodImage)
        val tvRecipeName = dialogView.findViewById<TextView>(R.id.tvRecipeName)
        val tvRecipeDescription = dialogView.findViewById<TextView>(R.id.tv_description)
        val rvIngredients = dialogView.findViewById<RecyclerView>(R.id.rv_ingredients)
        val rvSteps = dialogView.findViewById<RecyclerView>(R.id.rv_steps)
        val tvCalories = dialogView.findViewById<TextView>(R.id.tv_calories_value)

        //        condition
        var isLowcal = false
        var isFavorite = false



        //        setup
        Glide.with(ivRecipe)
            .load(recipe.recommended.photoUrl)
            .into(ivRecipe)
        tvRecipeName.text = recipe.recommended.title
        tvRecipeDescription.text = recipe.recommended.description

        val layoutManager = GridLayoutManager(requireContext(), 2)
        val stepsLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvIngredients.layoutManager = layoutManager
        rvIngredients.adapter = ingredientsAdapter

        rvSteps.adapter = stepsAdapter
        rvSteps.layoutManager = stepsLayoutManager

        tvCalories.text = recipe.recommended.calories.toString()



        val alertDialog = dialogBuilder.setView(dialogView).create()

        lowcalBtn.setOnClickListener {
            if (isLowcal) {
                lowcalBtn.setImageResource(R.drawable.ic_food)
                isLowcal = false
                rvIngredients.adapter = ingredientsAdapter
                tvCalories.text = recipe.recommended.calories.toString()
                rvSteps.adapter = stepsAdapter

            } else {
                lowcalBtn.setImageResource(R.drawable.ic_food_healthy)
                isLowcal = true
                rvIngredients.adapter = lowCalIngAdapter
                tvCalories.text = recipe.recommended.healthyCalories.toString()
                rvSteps.adapter = lowCalStepsAdapter
            }
        }

        backBtn.setOnClickListener {
            alertDialog.dismiss()
        }

        favBtn.setOnClickListener {
            if (isFavorite) {
                favBtn.setImageResource(R.drawable.ic_favorite_border)
                isFavorite = false
                viewModel.delete(
                    FavoriteRecipe(
                        recipe.recommended.id,
                        recipe.recommended.title,
                        recipe.recommended.description,
                        recipe.recommended.photoUrl,
                        recipe.recommended.ingredients,
                        recipe.recommended.steps,
                        recipe.recommended.healthyIngredients,
                        recipe.recommended.healthySteps,
                        recipe.recommended.calories,
                        recipe.recommended.healthyCalories,
                    )
                )
            } else {
                favBtn.setImageResource(R.drawable.ic_favorite_fill)
                isFavorite = true
                viewModel.insert(
                    FavoriteRecipe(
                        recipe.recommended.id,
                        recipe.recommended.title,
                        recipe.recommended.description,
                        recipe.recommended.photoUrl,
                        recipe.recommended.ingredients,
                        recipe.recommended.steps,
                        recipe.recommended.healthyIngredients,
                        recipe.recommended.healthySteps,
                        recipe.recommended.calories,
                        recipe.recommended.healthyCalories,
                    )
                )
            }
            isFavorite = !isFavorite
        }




        alertDialog.show()
    }

}