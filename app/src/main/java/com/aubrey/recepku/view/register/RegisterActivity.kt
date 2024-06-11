package com.aubrey.recepku.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.databinding.ActivityRegisterBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
        toLoginPage()
        playAnimation()
    }

    private fun setupAction() {
        binding.buttonRegister.setOnClickListener {
            val username = binding.usernameRegister.text.toString()
            val password = binding.passwordRegister.text.toString()
            val email = binding.emailRegister.text.toString()

            viewModel.register(username, password, email).observe(this) { regist ->
                when (regist) {
                    is Result.Loading -> {
                        binding.progressRegister.visibility = View.VISIBLE
                    }


                    is Result.Success<*> -> {
                        binding.progressRegister.visibility = View.INVISIBLE
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeay")
                            setMessage("Anda berhasil mendaftar. Sudah siap menjelajah?")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(context, LoginActivity::class.java)
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
                        binding.progressRegister.visibility = View.INVISIBLE
                        val error = regist.error
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun toLoginPage(){
        binding.toLoginPage.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {

        val logo = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(300)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val desc = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(300)
        val login = ObjectAnimator.ofFloat(binding.buttonRegister, View.ALPHA, 1f).setDuration(300)
        val user = ObjectAnimator.ofFloat(binding.usernameText, View.ALPHA, 1f).setDuration(300)
        val username = ObjectAnimator.ofFloat(binding.usernameLayout, View.ALPHA, 1f).setDuration(300)
        val userRegister = ObjectAnimator.ofFloat(binding.usernameRegister, View.ALPHA, 1f).setDuration(300)
        val emailTxt = ObjectAnimator.ofFloat(binding.emailText, View.ALPHA, 1f).setDuration(300)
        val email = ObjectAnimator.ofFloat(binding.emailLayout, View.ALPHA, 1f).setDuration(300)
        val emailRegister = ObjectAnimator.ofFloat(binding.emailRegister, View.ALPHA, 1f).setDuration(300)
        val passTxt = ObjectAnimator.ofFloat(binding.passwordText, View.ALPHA, 1f).setDuration(300)
        val password = ObjectAnimator.ofFloat(binding.passwordLayout, View.ALPHA, 1f).setDuration(300)
        val passRegister = ObjectAnimator.ofFloat(binding.passwordRegister, View.ALPHA, 1f).setDuration(300)
        val dontHave = ObjectAnimator.ofFloat(binding.alreadyText, View.ALPHA, 1f).setDuration(300)
        val registerpage = ObjectAnimator.ofFloat(binding.toLoginPage, View.ALPHA, 1f).setDuration(300)


        val emailtgt = AnimatorSet().apply {
            playTogether(email,emailTxt, emailRegister)
        }

        val usertgt = AnimatorSet().apply {
            playTogether(username,user, userRegister)
        }

        val passtgt = AnimatorSet().apply {
            playTogether(password,passTxt, passRegister)
        }

        AnimatorSet().apply {
            playSequentially(logo, title, desc, emailtgt, usertgt, passtgt, login, dontHave, registerpage)
            start()
        }
    }
}

