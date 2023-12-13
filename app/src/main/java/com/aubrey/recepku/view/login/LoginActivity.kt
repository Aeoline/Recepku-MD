package com.aubrey.recepku.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.aubrey.recepku.MainActivity
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.databinding.ActivityLoginBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>{
        ViewModelFactory.getInstance(this)
    }

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
            setupLogin()
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

    private fun setupLogin(){
        binding.loginButton.setOnClickListener {
            val username = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            viewModel.login(username, password).observe(this){session->
                when(session){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeay!")
                            setMessage("Anda berhasil login")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        val error = session.error
                        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }
}