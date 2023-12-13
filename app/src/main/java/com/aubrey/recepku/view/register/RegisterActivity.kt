package com.aubrey.recepku.view.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aubrey.recepku.databinding.ActivityRegisterBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.login.LoginActivity
import com.aubrey.recepku.data.common.Result

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

        binding.toLoginPage.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAction(){
        binding.buttonRegister.setOnClickListener {
            val username = binding.usernameRegister.text.toString()
            val password = binding.passwordRegister.text.toString()
            val email = binding.emailRegister.text.toString()

            viewModel.register(username, password, email).observe(this){regist->
                when(regist){
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
}