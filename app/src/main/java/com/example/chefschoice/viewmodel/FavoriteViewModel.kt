package com.example.chefschoice.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chefschoice.database.FavoriteDao
import com.example.chefschoice.database.FavoriteEntity
import kotlinx.coroutines.flow.Flow

class FavoriteViewModel(private val favoriteDao: FavoriteDao):ViewModel() {

   fun showFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getAll()
   suspend fun deleteFavorite(favoriteEntity: FavoriteEntity){
      favoriteDao.delete(favoriteEntity)
   }

   fun recipeExists(id: Int): Boolean{
      return favoriteDao.recipeExists(id)
}
}