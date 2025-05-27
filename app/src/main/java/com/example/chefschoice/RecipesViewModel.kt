package com.example.chefschoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefschoice.database.FavoriteDao
import com.example.chefschoice.database.FavoriteEntity
import com.example.chefschoice.network.*
import kotlinx.coroutines.launch

// RecipesViewModel is attached to the RecipesFragment
class RecipesViewModel(val favoriteDao: FavoriteDao) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    private val _photos = MutableLiveData<List<FavoriteEntity>>()
    private val _random = MutableLiveData<RandomRecipe>()
    private val _card = MutableLiveData<RecipeCard>()
    private val _barcode = MutableLiveData<Upc>()


    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status
    val photos: LiveData<List<FavoriteEntity>> = _photos
    val random: LiveData<RandomRecipe> = _random
  val card: LiveData<RecipeCard> = _card
     val barcode: LiveData<Upc> = _barcode

//preload. first call
      //init {
    //getRandomRecipe()
  //  }

    fun getFoodBarcode(upc: String) {
         viewModelScope.launch {
             _barcode.value = ChefApi.retrofitService.getBarcode(upc)
         }
     }
    fun getRecipeCard(recipeId: String) {
        viewModelScope.launch {
            _card.value = ChefApi.retrofitService.getRecipe(recipeId)
        }
    }
        fun getChefPhotos(ingredient: String) {

            viewModelScope.launch {
                try {
                    _photos.value = ChefApi.retrofitService.getPhotos(ingredient)
                    _status.value = "Success: Recipes retrieved"
                } catch (e: Exception) {
                    _status.value = "Failure: ${e.message}"
                }
            }
        }

        fun getRandomRecipe() {
            viewModelScope.launch {
                _random.value = ChefApi.retrofitService.getRandom()
            }
        }

        suspend fun insert(favoriteEntity: FavoriteEntity) {
            favoriteDao.insert(favoriteEntity)
        }
    fun recipeExists(id: Int): Boolean{
        return favoriteDao.recipeExists(id)
    }
    }




