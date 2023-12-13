package com.aubrey.recepku.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.databinding.FragmentHomeBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.adapter.RecipeAdapter
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aubrey.recepku.R
import com.aubrey.recepku.view.adapter.RecommendedRecipeAdapter
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.data.model.recipe.Favorite
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.view.SettingViewModel
import com.aubrey.recepku.view.adapter.IngredientsAdapter
import com.aubrey.recepku.view.adapter.LowCalIngredientsAdapter
import com.aubrey.recepku.view.adapter.LowCalStepsAdapter
import com.aubrey.recepku.view.adapter.StepsAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

interface RecipeClickListener {
    fun onRecipeClicked(recipe: Favorite)
}

interface RecommendedRecipeClickListener {
    fun onRecipeClicked(recipe: Recommended)
}

class HomeFragment : Fragment(), RecipeClickListener, RecommendedRecipeClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var imageSlider: ImageSlider
    private lateinit var adapter: RecipeAdapter
    private lateinit var recommendedAdapter: RecommendedRecipeAdapter

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvRecipe.layoutManager = layoutManager
        imageSlider = binding.slider

        adapter = RecipeAdapter(this)
        binding.rvRecipe.adapter = adapter

        val recommendedLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvRecommended.layoutManager = recommendedLayoutManager

        recommendedAdapter = RecommendedRecipeAdapter(this)
        binding.rvRecommended.adapter = recommendedAdapter

        setupImageSlider()
        getRecipes()
        getRecommendedRecipes()
        searchBar()
        setDarkMode()
    }

    private fun setupImageSlider() {
        val imageList = listOf(
            SlideModel("https://www.herofincorp.com/public/admin_assets/upload/blog/64b91a06ab1c8_food%20business%20ideas.webp"),
            SlideModel("https://cdn.themistakenman.com/wp-content/uploads/2022/08/fast-food-business.webp"),
            SlideModel("https://images.bizbuysell.com/shared/listings/204/2045821/1d720199-35d9-4667-876a-03ef268f58f0-W336.jpg")
        )
        imageSlider.setImageList(imageList)
    }

    private fun getRecipes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is Result.Loading -> {
                        viewModel.getAllRecipes()
                        Log.d("HomeScreen", "Loading: Sabar")
                    }

                    is Result.Success -> {
                        adapter.submitList(uiState.data)
                        Log.d("HomeScreen", "Success: Nih Resep")
                    }

                    is Result.Error -> {
                        Log.d("HomeScreen", "Error: Kok gabisa si")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun getRecommendedRecipes() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState1.collect { uiState1 ->
                when (uiState1) {
                    is Result.Loading -> {
                        viewModel.getAllRecommendedRecipes()
                        Log.d("Recommended HomeScreen", "Loading: Sabar")
                    }

                    is Result.Success -> {
                        recommendedAdapter.submitList(uiState1.data)
                        // Tampilkan daftar resep yang direkomendasikan
                        // menggunakan recommendedRecipes
                        Log.d("Recommended HomeScreen", "Success: Nih Resep")
                    }

                    is Result.Error -> {
                        // Tindakan yang diambil saat terjadi kesalahan
                        Log.d("Recommended HomeScreen", "Error: Kok gabisa si")
                    }

                    else -> {}
                }
            }
        }
    }
    //Belum bisa
    private fun searchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { view, actionId, event ->
                searchBar.setText(searchView.text)
                searchView.hide()
                rvRecipe.adapter = RecipeAdapter(recipeClickListener = this@HomeFragment)

                // Menggunakan CoroutineScope untuk membuat coroutine
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    viewModel.searchRecipe(searchBar.text.toString())
                    viewModel.uiState.collect { uiState ->
                        when (uiState) {
                            is Result.Loading -> {
                                viewModel.searchRecipe(searchBar.text.toString())
                                Log.d("Search HomeScreen", "Loading: Sabar")
                            }

                            is Result.Success -> {
                                adapter.submitList(uiState.data)
                                Log.d("Search HomeScreen", "Success: Nih Resep")
                            }

                            is Result.Error -> {
                                Log.d("Search HomeScreen", "Error: Kok gabisa si")
                            }

                            else -> {}
                        }
                    }
                }
                false
            }
        }
    }

    override fun onRecipeClicked(recipe: Favorite) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.item_card_detail, null)

//      Adapter
        val ingredientsAdapter = recipe.recipe.ingredients?.let { IngredientsAdapter(it) }
        val stepsAdapter = recipe.recipe.steps?.let { StepsAdapter(it) }
        val lowCalIngAdapter = recipe.recipe.healthyIngredients?.let { LowCalIngredientsAdapter(it) }
        val lowCalStepsAdapter = recipe.recipe.healthySteps?.let { LowCalStepsAdapter(it) }

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
        var isFavorite = false

//        setup
        Glide.with(ivRecipe)
            .load(R.drawable.menu)
            .into(ivRecipe)
        tvRecipeName.text = recipe.recipe.title
        tvRecipeDescription.text = recipe.recipe.description

        val layoutManager = GridLayoutManager(requireContext(), 2)
        val stepsLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvIngredients.layoutManager = layoutManager
        rvIngredients.adapter = ingredientsAdapter

        rvSteps.adapter = stepsAdapter
        rvSteps.layoutManager = stepsLayoutManager

        tvCalories.text = recipe.recipe.calories.toString()



        val alertDialog = dialogBuilder.setView(dialogView).create()

        lowcalBtn.setOnClickListener {
            if (isLowcal) {
                lowcalBtn.setImageResource(R.drawable.ic_food)
                isLowcal = false
                rvIngredients.adapter = ingredientsAdapter
                tvCalories.text = recipe.recipe.calories.toString()
                rvSteps.adapter = stepsAdapter

            } else {
                lowcalBtn.setImageResource(R.drawable.ic_food_healthy)
                isLowcal = true
                rvIngredients.adapter = lowCalIngAdapter
                tvCalories.text = recipe.recipe.healthyCalories.toString()
                rvSteps.adapter = lowCalStepsAdapter
            }
        }

        backBtn.setOnClickListener {
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
        val tvCalories = dialogView.findViewById<TextView>(R.id.tv_calories)

        //        condition
        var isLowcal = false
        var isFavorite = false


        //        setup
        Glide.with(ivRecipe)
            .load(R.drawable.menu)
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

        alertDialog.show()
    }

    private fun setDarkMode(){
        val viewModel by viewModels<SettingViewModel> {
            ViewModelFactory.getInstance(requireActivity().application)
        }
        viewModel.getThemeSetting().observe(viewLifecycleOwner){
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}