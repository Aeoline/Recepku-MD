package com.aubrey.recepku.view.search

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.ActivitySearchBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.data.common.Result

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }
    private val searchRecipeAdapter = SearchRecipeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        val searchQuery = intent.getStringExtra("searchQuery")
        if (searchQuery != null) {
            searchRecipe(searchQuery)
        }
    }

    private fun setupRecyclerView() {
        binding.rvRecipes.apply {
            adapter = searchRecipeAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }

    private fun searchRecipe(query: String) {
        viewModel.searchRecipe(query).observe(this@SearchActivity) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                    Log.d("com.aubrey.recepku.view.search.SearchActivity", "Loading...")
                }
                is Result.Error -> {
                    val error = result.error
                    Toast.makeText(this@SearchActivity, "error", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                    Log.d("com.aubrey.recepku.view.search.SearchActivity", "Error: $error")
                }
                is Result.Success<*> -> {
                    binding.progressBar.isVisible = false
                    val data = result.data as? List<DataItem>
                    searchRecipeAdapter.submitList(data)
                    Log.d("com.aubrey.recepku.view.search.SearchActivity", "Success: $data")
                }
            }
        }
    }
}