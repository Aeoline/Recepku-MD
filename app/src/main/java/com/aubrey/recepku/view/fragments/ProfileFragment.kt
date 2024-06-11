package com.aubrey.recepku.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.aubrey.recepku.R
import com.aubrey.recepku.data.userpref.UserPreferences
import com.aubrey.recepku.data.userpref.dataStore
import com.aubrey.recepku.databinding.FragmentProfileBinding
import com.aubrey.recepku.view.welcome_page.WelcomeActivity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutButton.setOnClickListener {
            logout()
        }

        setupView()
    }

    private fun setupView() {

        val tvName = binding.usernameLabel
        val tvEmail = binding.emailLabel

        readUserData { username, email ->
            tvName.text = username
            tvEmail.text = email
        }
    }

    private fun logout() {
        val preference = UserPreferences.getInstance(requireActivity().application.dataStore)
        lifecycleScope.launch {
            preference.deleteCookies()
            Log.d("Deleted", "Cookies Deleted")
        }

        val intent = Intent(requireContext(), WelcomeActivity::class.java)
        startActivity(intent)
    }

    private fun readUserData(callback: (username: String, email: String) -> Unit) {
        val dataStore = requireContext().dataStore

        lifecycleScope.launch {
            val userData = dataStore.data.firstOrNull()

            val username = userData?.get(UserPreferences.NAME_KEY) ?: ""
            val email = userData?.get(UserPreferences.EMAIL_KEY) ?: ""

            callback(username, email)
        }
    }


}