package com.aubrey.recepku.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aubrey.recepku.data.Injection
import com.aubrey.recepku.data.repository.RecipeRepository
import com.aubrey.recepku.data.repository.UserRepository
import com.aubrey.recepku.data.userpref.UserPreferences
import com.aubrey.recepku.data.userpref.dataStore
import com.aubrey.recepku.view.detail.DetailViewModel
import com.aubrey.recepku.view.edituser.EditUserViewModel
import com.aubrey.recepku.view.favorite.FavoriteViewModel
import com.aubrey.recepku.view.home.HomeViewModel
import com.aubrey.recepku.view.login.LoginViewModel
import com.aubrey.recepku.view.register.RegisterViewModel
import com.aubrey.recepku.view.search.SearchViewModel
import com.aubrey.recepku.view.splashscreen.SplashScreenViewModel

class ViewModelFactory private constructor(private val repository: UserRepository,
                                           private val recipeRepository: RecipeRepository,
                                              private val userPreferences: UserPreferences
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
                @Suppress("UNCHECKED_CAST")
                HomeViewModel(recipeRepository,repository,userPreferences) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(repository,userPreferences) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->{
                FavoriteViewModel(recipeRepository) as T
            }
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) ->{
                SplashScreenViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) ->{
                SearchViewModel(recipeRepository) as T
            }
            modelClass.isAssignableFrom(EditUserViewModel::class.java) ->{
                EditUserViewModel(repository,userPreferences) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->{
                DetailViewModel(recipeRepository) as T
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
                        ,UserPreferences.getInstance(context.dataStore)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
