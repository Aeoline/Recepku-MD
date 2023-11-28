package com.aubrey.recepku.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.toRegisterPage.setOnClickListener {
            toRegisterPage()
        }

    }

    private fun toRegisterPage(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun login(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        Toast.makeText(this,"Welcome!",Toast.LENGTH_SHORT).show()
    }
}