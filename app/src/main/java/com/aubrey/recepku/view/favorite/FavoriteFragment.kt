package com.aubrey.recepku.view.favorite

import FavoriteAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.home.HomeViewModel

class FavoriteFragment : Fragment() {
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var rvFavorite: RecyclerView

    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        rvFavorite = view.findViewById(R.id.rvFavorite)
        // Atur layout manager untuk RecyclerView
        rvFavorite.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi adapter kosong
        favoriteAdapter = FavoriteAdapter()
        // Atur adapter ke RecyclerView
        rvFavorite.adapter = favoriteAdapter

        // Observasi data favorit dari ViewModel
        viewModel.getAllFavoriteRecipe().observe(viewLifecycleOwner) { favoriteRecipes ->
            favoriteAdapter.submitList(favoriteRecipes)
        }

        return view
    }
}