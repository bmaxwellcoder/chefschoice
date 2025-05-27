package com.example.chefschoice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chefschoice.database.FavoriteDao

class FavoriteViewModelFactory (

    private val favoriteDao: FavoriteDao
    ) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoriteViewModel(favoriteDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
