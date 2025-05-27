package com.example.chefschoice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chefschoice.RecipesViewModel
import com.example.chefschoice.database.FavoriteDao


class RecipesViewModelFactory (private val favoriteDao: FavoriteDao) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecipesViewModel(favoriteDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}