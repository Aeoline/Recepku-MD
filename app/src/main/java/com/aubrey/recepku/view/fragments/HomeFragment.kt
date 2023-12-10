package com.aubrey.recepku.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.databinding.FragmentHomeBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.adapter.RecipeAdapter
import com.aubrey.recepku.view.viewmodels.HomeViewModel
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aubrey.recepku.view.adapter.RecommendedRecipeAdapter
import com.aubrey.recepku.data.Result
import com.aubrey.recepku.data.model.recipe.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {
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

        adapter = RecipeAdapter()
        binding.rvRecipe.adapter = adapter // Menggunakan binding.rvRecipe


        val recommendedLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvRecommended.layoutManager = recommendedLayoutManager

        recommendedAdapter = RecommendedRecipeAdapter()
        binding.rvRecommended.adapter = recommendedAdapter // Menggunakan binding.rvRecommended

        setupImageSlider()
        getRecipes()
        getRecommendedRecipes()
        searchBar()
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
                rvRecipe.adapter = RecipeAdapter()

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

}