package com.aubrey.recepku.view.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.aubrey.recepku.R
import com.aubrey.recepku.databinding.ActivitySearchBinding
import com.aubrey.recepku.data.common.Result

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val viewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchQuery = intent.getStringExtra("searchQuery")
        if (searchQuery != null) {
            searchRecipe(searchQuery)
        }
    }

    private fun searchRecipe(query: String) {
        viewModel.searchRecipe(query).observe(this@SearchActivity) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Result.Error -> {
                    val error = result.error
                    Toast.makeText(this@SearchActivity, error, Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    val data = result.data

                    if (data != null) {
                        if (data.isEmpty()) {
                            binding.imgNotFound.isVisible = true
                        }
                    }

                    binding.rvRecipes.apply {
                        adapter = SearchRecipeAdapter(data)
                        layoutManager = LinearLayoutManager(this@SearchActivity)
                    }
                }
            }
        }
    }
}