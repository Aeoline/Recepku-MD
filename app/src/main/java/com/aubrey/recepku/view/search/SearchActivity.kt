package com.aubrey.recepku.view.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aubrey.recepku.R
import com.aubrey.recepku.data.response.DataItem
import com.aubrey.recepku.databinding.ActivitySearchBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.data.database.FavoriteRecipe
import com.aubrey.recepku.data.userpref.UserPreferences
import com.aubrey.recepku.data.userpref.dataStore
import com.aubrey.recepku.view.adapter.IngredientsAdapter
import com.aubrey.recepku.view.adapter.LowCalIngredientsAdapter
import com.aubrey.recepku.view.adapter.LowCalStepsAdapter
import com.aubrey.recepku.view.adapter.StepsAdapter
import com.aubrey.recepku.view.edituser.EditUserActivity
import com.aubrey.recepku.view.home.HomeViewModel
import com.aubrey.recepku.view.login.LoginActivity
import com.aubrey.recepku.view.setting.SettingActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch



class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    private val homeViewModel: HomeViewModel by viewModels {
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
        searchBar()
    }

    private fun searchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { view, actionId, event ->
//                val query = searchView.text.toString()
//                val intent = Intent(requireContext(), SearchActivity::class.java)
//                intent.putExtra("searchQuery", "$query")
//                startActivity(intent)
                searchBar.setText(searchView.text)
                searchView.hide()
                searchRecipe(searchView.text.toString())
                false
            }
//            searchBar.inflateMenu(R.menu.option_menu)
//            searchBar.setOnMenuItemClickListener { menuItem ->
//                when (menuItem.itemId) {
//                    R.id.profile_icon -> {
//                        showProfile()
//                        true
//                    }
//
//                    else -> false
//                }
//            }
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

    @SuppressLint("MissingInflatedId")
    private fun showProfile() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.card_profile, null)
        val tvName = dialogView.findViewById<TextView>(R.id.tv_name)
        val tvEmail = dialogView.findViewById<TextView>(R.id.tv_email)
        val settingBtn = dialogView.findViewById<ImageButton>(R.id.btn_setting)
        val backBtn = dialogView.findViewById<ImageButton>(R.id.btn_back_detail)
        val logoutBtn = dialogView.findViewById<CardView>(R.id.cardLogout)
        val setAccount = dialogView.findViewById<CardView>(R.id.cardAccount)
        val deleteAccount = dialogView.findViewById<CardView>(R.id.cardDeleteAccount)



        setAccount.setOnClickListener {
            val intent = Intent(this, EditUserActivity::class.java)
            startActivity(intent)
        }

        readUserData { username, email ->
            tvName.text = username
            tvEmail.text = email
        }

        val alertDialog = dialogBuilder.setView(dialogView).create()
        settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
            alertDialog.dismiss()
        }

        backBtn.setOnClickListener {
            alertDialog.dismiss()
        }

        logoutBtn.setOnClickListener {
            val preference = UserPreferences.getInstance(this.application.dataStore)
            lifecycleScope.launch {
                preference.deleteCookies()
                Log.d("Deleted", "Cookies Deleted")
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun readUserData(callback: (username: String, email: String) -> Unit) {
        val dataStore = this.dataStore

        lifecycleScope.launch {
            val userData = dataStore.data.firstOrNull()

            val username = userData?.get(UserPreferences.NAME_KEY) ?: ""
            val email = userData?.get(UserPreferences.EMAIL_KEY) ?: ""

            callback(username, email)
        }
    }

}