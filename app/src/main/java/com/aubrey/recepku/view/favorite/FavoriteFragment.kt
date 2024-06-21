package com.aubrey.recepku.view.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.databinding.FragmentFavoriteBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.detail.DetailActivity
import com.aubrey.recepku.data.common.Result



class FavoriteFragment : Fragment() {
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var rvFavorite: RecyclerView
    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        rvFavorite = binding.rvFavorite

        rvFavorite.layoutManager = LinearLayoutManager(requireContext())

        favoriteAdapter = FavoriteAdapter()
        rvFavorite.adapter = favoriteAdapter

        getFavoriteRecipes()

        return view
    }

    private fun getFavoriteRecipes() {
        viewModel.getAllFavoriteRecipe().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // Tampilkan loading indicator
                    Log.d("FavoriteRecipes", "Loading favorite recipes...")
                    binding.noFavorite.visibility = View.GONE
                }
                is Result.Success -> {
                    val favoriteRecipes = result.data
                    // Tambahkan logging untuk memastikan data yang diterima
                    Log.d("FavoriteRecipes", "Received favorite recipes: $favoriteRecipes")

                    // Set adapter dengan data yang diterima
                    favoriteAdapter.setFavoriteRecipes(favoriteRecipes)
                    binding.noFavorite.visibility = if (favoriteRecipes.isEmpty()) View.VISIBLE else View.GONE
                }
                is Result.Error -> {
                    // Tangani error
                    Log.e("FavoriteRecipes", "Error fetching favorite recipes")
                    // Set adapter dengan list kosong atau tampilkan pesan error
                    binding.noFavorite.visibility = View.VISIBLE
                    favoriteAdapter.setFavoriteRecipes(emptyList())
                    // Optionally show a message to the user
                    Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




    private fun navigateToDetailActivity(recipe: FavoriteRecipe) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("recipe", recipe)
        startActivity(intent)
    }
}





