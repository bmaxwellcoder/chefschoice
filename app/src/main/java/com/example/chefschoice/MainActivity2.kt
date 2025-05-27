package com.example.chefschoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        // val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        //  val navController = findNavController(R.id.fragmentContainerView)

        //   bottomNavigationView.setupWithNavController(navController)
        val homeFragment = HomeFragment()
        val recipesFragment = RecipesFragment()
        val favoritesFragment = FavoritesFragment()
        val infoFragment = InfoFragment()

        makeCurrentFragment(homeFragment)


        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_recipes -> makeCurrentFragment(recipesFragment)
                R.id.ic_favorites -> makeCurrentFragment(favoritesFragment)
                R.id.ic_info -> makeCurrentFragment(infoFragment)
            }
            true
        }
    }
        private fun makeCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, fragment)
                commit()
            }
    }
