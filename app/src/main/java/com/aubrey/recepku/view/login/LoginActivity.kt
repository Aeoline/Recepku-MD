package com.aubrey.recepku.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aubrey.recepku.MainActivity
import com.aubrey.recepku.R
import com.aubrey.recepku.databinding.ActivityLoginBinding
import com.aubrey.recepku.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toRegisterPage()
        login()
    }

    private fun toRegisterPage(){
        val toRegisterBtn = binding.toRegisterPage

        toRegisterBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(){
        val loginBtn = binding.loginButton

        loginBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}