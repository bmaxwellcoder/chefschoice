package com.example.chefschoice

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import coil.load
import com.example.chefschoice.database.RecipesApplication
import com.example.chefschoice.viewmodel.RecipesViewModelFactory


class MainActivity4 : AppCompatActivity() {
    private val viewModel: RecipesViewModel by viewModels() {
        RecipesViewModelFactory((this.application as RecipesApplication).database.favoriteDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val recipeId = intent.getStringExtra("id")   // returns null bc need .toString in adapter

        if (recipeId != null) {
            viewModel.getRecipeCard(recipeId)
            //Coil
            viewModel.card.observe(this) {
                findViewById<ImageView>(R.id.imageView).load(
                    it?.url?.toUri()?.buildUpon()?.scheme("https")?.build()
                )

            }
        }
    }
}



