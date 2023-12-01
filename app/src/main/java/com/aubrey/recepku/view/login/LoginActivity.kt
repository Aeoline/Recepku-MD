package com.aubrey.recepku.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
        playAnimation()
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

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.ROTATION, 0f, 360f).apply {
            duration = 350
            repeatCount = 1
            repeatMode = ObjectAnimator.RESTART
        }.start()

        val sakura = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val desc = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)
        val emailTxt = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val email = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val passTxt = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val password = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val dontHave = ObjectAnimator.ofFloat(binding.dontHaveAcc, View.ALPHA, 1f).setDuration(300)
        val registerpage = ObjectAnimator.ofFloat(binding.toRegisterPage, View.ALPHA, 1f).setDuration(300)

        val emailtgt = AnimatorSet().apply {
            playTogether(email,emailTxt)
        }

        val passtgt = AnimatorSet().apply {
            playTogether(password,passTxt)
        }

        AnimatorSet().apply {
            playSequentially(sakura, title, desc, emailtgt, passtgt, login, dontHave, registerpage)
            start()
        }
    }
}