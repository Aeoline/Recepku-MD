package com.aubrey.recepku.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.aubrey.recepku.MainActivity
import com.aubrey.recepku.R
import com.aubrey.recepku.data.retrofit.ApiConfig
import com.aubrey.recepku.data.userpref.ProfileModel
import com.aubrey.recepku.databinding.ActivitySplashScreenBinding
import com.aubrey.recepku.view.ViewModelFactory
import com.aubrey.recepku.view.login.LoginActivity
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    private val viewModel by viewModels<SplashScreenViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        checkCookies()

    }

    private fun checkCookies(){
        lifecycleScope.launch {
            viewModel.getCookie().observe(this@SplashScreenActivity) { cookie: ProfileModel ->
                ApiConfig.cookie = cookie.cookie.toString()
                val delayRunnable = Runnable {
                    if (cookie.cookie.toString().isNotEmpty()) {
                        navigateToMainActivity()
                    } else {
                        navigateToLoginActivity()
                    }
                }
                Handler(Looper.getMainLooper()).postDelayed(delayRunnable, DELAY_TIME)
            }
        }
    }

    private fun navigateToMainActivity() {
        val mainIntent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun navigateToLoginActivity() {
        val authIntent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
        startActivity(authIntent)
        finish()
    }

    companion object {
        const val DELAY_TIME: Long = 2000
    }
}