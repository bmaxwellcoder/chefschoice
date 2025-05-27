package com.example.chefschoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chefschoice.network.*
import kotlinx.coroutines.launch

// PopularViewModel is attached to the HomeFragment
class PopularViewModel: ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _photos = MutableLiveData<ComplexSearch>()
    private val _trivia = MutableLiveData<Trivia>()
    private val _dessert = MutableLiveData<ComplexSearch>()
    private val _quick = MutableLiveData<ComplexSearch>()


    // The external immutable LiveData for the request status
    val photos: LiveData<ComplexSearch> = _photos
    val trivia: LiveData<Trivia> = _trivia
    val dessert : LiveData<ComplexSearch> = _dessert
    val quick: LiveData<ComplexSearch> = _quick


    //preload. first call
    init {
        getPopularRecipes()
        getFoodTrivia()
        getDessertRecipes()
        getQuickRecipes()
    }

     private fun getPopularRecipes() {
        viewModelScope.launch {
          _photos.value = ChefApi.retrofitService.getPopular()

            }
        }

    private fun getQuickRecipes() {
        viewModelScope.launch {
            _quick.value = ChefApi.retrofitService.getQuick()

        }
    }
    private fun getDessertRecipes() {
        viewModelScope.launch {
            _dessert.value = ChefApi.retrofitService.getDessert()
        }
        }
    private fun getFoodTrivia() {
        viewModelScope.launch {
            _trivia.value = ChefApi.retrofitService.getTrivia()

        }
    }
}