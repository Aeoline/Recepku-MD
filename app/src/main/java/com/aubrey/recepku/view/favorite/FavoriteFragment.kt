package com.aubrey.recepku.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.response.RecipeResponse

class FavoriteFragment : Fragment() {
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var rvFavorite: RecyclerView

    val response = RecipeResponse()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        rvFavorite = view.findViewById(R.id.rvFavorite)
        // Atur layout manager untuk RecyclerView
        rvFavorite.layoutManager = LinearLayoutManager(requireContext())

        // Filter daftar resep berdasarkan isFavorite
        val favoriteRecipeList = response?.data?.filter { it?.isFavorite == true }

        // Buat instance adapter dan teruskan data resep favorit
        favoriteAdapter = FavoriteAdapter(favoriteRecipeList)
        // Atur adapter ke RecyclerView
        rvFavorite.adapter = favoriteAdapter
        return view
    }
}