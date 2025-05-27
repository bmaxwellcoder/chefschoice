package com.example.chefschoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chefschoice.database.RecipesApplication
import com.example.chefschoice.databinding.FragmentFavoritesBinding
import com.example.chefschoice.viewmodel.FavoriteViewModel
import com.example.chefschoice.viewmodel.FavoriteViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    val binding get() = _binding!!
    val viewModel: FavoriteViewModel by activityViewModels() {
        FavoriteViewModelFactory((activity?.application as RecipesApplication).database.favoriteDao())
    }
    //val viewModel2: FavoriteViewModel = ViewModelProvider(this@FavoritesFragment)[FavoriteViewModel::class.java]

    private lateinit var recyclerView: RecyclerView
    val favoriteAdapter = FavoriteAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = favoriteAdapter
        showFavorites()
        binding.recyclerView.adapter = favoriteAdapter
        favoriteAdapter.onDeleteClickListener {
            lifecycleScope.launch {
                viewModel.deleteFavorite(it)
                Snackbar.make(requireView(), "DELETED", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showFavorites() {

        lifecycle.coroutineScope.launch {
            viewModel.showFavorites().collect {
                favoriteAdapter.submitList(it)
            }
        }
    }
}