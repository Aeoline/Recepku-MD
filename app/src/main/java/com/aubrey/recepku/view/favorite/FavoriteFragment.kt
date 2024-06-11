package com.aubrey.recepku.view.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.model.recommended.Recommended
import com.aubrey.recepku.databinding.FragmentFavoriteBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.detail.DetailActivity



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
        viewModel.getAllFavoriteRecipe().observe(viewLifecycleOwner) { favoriteRecipes ->
            favoriteAdapter.setFavoriteRecipes(favoriteRecipes)
        }
    }

    private fun navigateToDetailActivity(recipe: FavoriteRecipe) {
        val intent = Intent(requireContext(), DetailActivity::class.java)
        intent.putExtra("recipe", recipe)
        startActivity(intent)
    }
}





