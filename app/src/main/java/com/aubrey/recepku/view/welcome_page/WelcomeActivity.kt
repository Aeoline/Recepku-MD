package com.aubrey.recepku.view.welcome_page

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aubrey.recepku.R
import com.aubrey.recepku.databinding.ActivityWelcomeBinding
import com.aubrey.recepku.view.login.LoginActivity
import com.aubrey.recepku.view.register.RegisterActivity
import com.bumptech.glide.request.transition.ViewPropertyTransition.Animator
import com.google.android.material.animation.AnimatorSetCompat.playTogether

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

        playAnimation()


    }

    private fun login(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
    }

    private fun register(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun playAnimation(){
        val logo = ObjectAnimator.ofFloat(binding.imageView, "alpha", 0f, 1f).setDuration(300)
        val recepku = ObjectAnimator.ofFloat(binding.tvRecepku, "alpha", 0f, 1f).setDuration(300)
        val slogan = ObjectAnimator.ofFloat(binding.tvSlogan, "alpha", 0f, 1f).setDuration(300)
        val anywhere = ObjectAnimator.ofFloat(binding.slogan2, "alpha", 0f, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.loginButton, "alpha", 0f, 1f).setDuration(300)
        val register = ObjectAnimator.ofFloat(binding.registerButton, "alpha", 0f, 1f).setDuration(300)

        val together = AnimatorSet().apply {
            playTogether(login,register)
            start()
        }
        AnimatorSet().apply {
            playSequentially(logo,recepku,slogan,anywhere,together)
            start()
        }
    }

}