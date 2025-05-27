package com.example.chefschoice


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.chefschoice.database.RecipesApplication
import com.example.chefschoice.databinding.FragmentRecipesBinding
import com.example.chefschoice.viewmodel.RecipesViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class RecipesFragment : Fragment() {
    val adapter = RecipesListAdapter()


    private val viewModel: RecipesViewModel by activityViewModels {
        RecipesViewModelFactory((activity?.application as RecipesApplication).database.favoriteDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentRecipesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        //Get user input from searchview & query the api to get the results
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit( query: String): Boolean {
                Log.i("Bryan", query)
                viewModel.getChefPhotos(query)
             // Show toast if api returns empty list (ex. injera) however showing on first search
                //   if(binding.recyclerView.isEmpty()){
              //      Toast.makeText(activity, "No recipes found, please try another ingredient.", Toast.LENGTH_LONG)
              //          .show()
             //   }
                return true

            }
            override fun onQueryTextChange(s: String?): Boolean {
                return false

            }

        })


        binding.recyclerView.adapter = adapter
        adapter.onSaveClickListener {
            lifecycleScope.launch {
                viewModel.insert(it)
                Snackbar.make(requireView(), "SAVED", Snackbar.LENGTH_SHORT).show()
            }
        }

         binding.button.setOnClickListener {
            val intent = Intent(activity, MainActivity3::class.java)
             activity?.startActivity(intent)
        }




        return binding.root
    }


}









