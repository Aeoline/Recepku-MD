package com.aubrey.recepku.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aubrey.recepku.data.Injection
import com.aubrey.recepku.data.repository.RecipeRepository
import com.aubrey.recepku.data.repository.UserRepository
import com.aubrey.recepku.view.edituser.EditUserViewModel
import com.aubrey.recepku.view.favorite.FavoriteViewModel
import com.aubrey.recepku.view.home.HomeViewModel
import com.aubrey.recepku.view.login.LoginViewModel
import com.aubrey.recepku.view.register.RegisterViewModel

class ViewModelFactory private constructor(private val repository: UserRepository,
                                           private val recipeRepository: RecipeRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(recipeRepository,repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->{
                FavoriteViewModel(recipeRepository) as T
            }
            modelClass.isAssignableFrom(EditUserViewModel::class.java) -> {
                EditUserViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class:"+modelClass.name)
        }
    }

    companion object {
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context),
                        Injection.provideRecipeRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}


