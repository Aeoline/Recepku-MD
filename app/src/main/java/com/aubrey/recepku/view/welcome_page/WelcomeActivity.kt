package com.aubrey.recepku.view.welcome_page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aubrey.recepku.R
import com.aubrey.recepku.databinding.ActivityWelcomeBinding
import com.aubrey.recepku.view.login.LoginActivity
import com.aubrey.recepku.view.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.registerButton.setOnClickListener {
            register()
        }


    }

    private fun login(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
    }

    private fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}