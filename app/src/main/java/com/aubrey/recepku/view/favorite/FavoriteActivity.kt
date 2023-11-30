package com.aubrey.recepku.view.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aubrey.recepku.R
import com.aubrey.recepku.databinding.ActivityRegisterBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Your Favorite Recipe <3"
    }
}