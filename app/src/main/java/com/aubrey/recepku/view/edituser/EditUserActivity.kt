package com.aubrey.recepku.view.edituser

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.aubrey.recepku.MainActivity
import com.aubrey.recepku.R
import com.aubrey.recepku.data.common.Result
import com.aubrey.recepku.databinding.ActivityEditUserBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserBinding
    private val viewModel by viewModels<EditUserViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.saveUser()

        binding.cardUsername.setOnClickListener {
            changeUsername()
        }

        binding.cardPassword.setOnClickListener {
            changePassword()
        }
    }


    private fun changeUsername(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.card_change_username, null)
        val edUsername = dialogView.findViewById<TextInputEditText>(R.id.edUsername)
        val edPassword = dialogView.findViewById<TextInputEditText>(R.id.edPassword)
        val button = dialogView.findViewById<Button>(R.id.btnChangeUsername)
        val alertDialog = dialogBuilder.setView(dialogView).create()

        button.setOnClickListener {
            val username = edUsername.text.toString()
            val password = edPassword.text.toString()
            viewModel.editUser(username, password).observe(this) {
                when (it) {
                    is Result.Loading -> {
                        Log.d("EditUserActivity", "Sabar Loading..")
                    }

                    is Result.Success -> {
                        AlertDialog.Builder(this).apply {
                            setTitle("Berhasil!")
                            setMessage("Yeay, Username kamu sudah berubah")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(this@EditUserActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            create().show()
                        }
                    }

                    is Result.Error -> {
                        Log.e("EditUserActivity", "Kok isoo yaa")
                    }
                }
            }
        }
        alertDialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun changePassword(){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.card_change_password, null)
        val alertDialog = dialogBuilder.setView(dialogView).create()
        val button = dialogView.findViewById<Button>(R.id.btnChangePassword)
        val edNewPassword = dialogView.findViewById<TextInputEditText>(R.id.edNewPassword)
        val edConfirmPassword = dialogView.findViewById<TextInputEditText>(R.id.edConfirmPassword)
        val edPassword = dialogView.findViewById<TextInputEditText>(R.id.edPassword)

        button.setOnClickListener {
            val confirmPassword = edPassword.text.toString()
            val password = edNewPassword.text.toString()
            val newPassword = edConfirmPassword.text.toString()

            viewModel.editPass(password,newPassword,confirmPassword).observe(this) {
                when (it) {
                    is Result.Loading -> {
                        Log.d("EditUserActivity", "Sabar Loading..")
                    }
                    is Result.Success -> {
                        AlertDialog.Builder(this).apply {
                            setTitle("Berhasil!")
                            setMessage("Yeay, Username kamu sudah berubah")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(this@EditUserActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            create().show()
                        }
                    }

                    is Result.Error -> {
                        Log.e("EditUserActivity", "Kok isoo yaa")
                    }
                }
            }
        }
        alertDialog.show()
    }

}