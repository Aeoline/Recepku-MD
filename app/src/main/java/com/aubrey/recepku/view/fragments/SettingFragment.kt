package com.aubrey.recepku.view.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aubrey.recepku.databinding.FragmentSettingBinding
import com.aubrey.recepku.view.SettingViewModel
import com.aubrey.recepku.view.ViewModelFactory

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDarkMode()
        setLanguage()
    }

    private fun setLanguage(){
//        binding.cardLanguage.setOnClickListener {
//            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
//            startActivity(intent)
//        }
    }
    private fun setDarkMode(){
        val viewModel by viewModels<SettingViewModel> {
            ViewModelFactory.getInstance(requireActivity().application)
        }
        viewModel.getThemeSetting().observe(viewLifecycleOwner){
            if (it){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchDarkMode.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchDarkMode.isChecked = false
            }
        }
        binding.switchDarkMode.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }
}