package com.example.chefschoice

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chefschoice.database.FavoriteEntity
import com.example.chefschoice.databinding.RecipeListBinding

class RecipesListAdapter : ListAdapter<FavoriteEntity, RecipesListAdapter.RecipesViewHolder>(diffUtilCallback) {
    interface OnItemClickedListener {
        fun onItemClicked(index: Int)
    }

    companion object {
        private val diffUtilCallback = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity
            ): Boolean {
                return oldItem.image == newItem.image
            }
        }
    }

    class RecipesViewHolder(var view: RecipeListBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(favoriteEntity: FavoriteEntity) {
            view.photo = favoriteEntity
            view.executePendingBindings()
        }
        // val favorite=binding.checkBox

    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecipeListBinding>(
            inflater,
            R.layout.recipe_list,
            parent,
            false
        )
        return RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
        val viewModel: RecipesViewModel = ViewModelProvider(holder.itemView.context as MainActivity2)[RecipesViewModel::class.java]
        //ViewModelProvider(this@FavoritesFragment)[FavoriteViewModel::class.java]
        val id = recipe.id
        val recipeFavorited = if (id != null) {
            viewModel.recipeExists(id)
        } else { false }

        // Check the team's box if the ID is in database
        if (recipeFavorited) {
            holder.view.checkBox.isChecked = true
        }
        holder.view.checkBox.setOnClickListener {
            holder.view.checkBox.isChecked = recipe.favorite
            Log.i("Neariah", "Card clicked: " + recipe.favorite)
            onSaveClick?.let {
                recipe?.let { it1 ->
                    it(it1)
                }
            }
        }

        // When card is clicked, Extract the id to launch a new activity with recipe card (explicit intent)
        holder.itemView.setOnClickListener{ v ->
            val intent = Intent( v.context, MainActivity4::class.java)
            intent.putExtra("id", recipe.id.toString())
            v.context.startActivity(intent)
            Log.i("Neariah", "Card clicked: " + recipe.id)
        }


        //Implicit intent
        /*  holder.itemView.setOnClickListener { v ->
              val queryUrl: Uri = Uri.parse("${MainActivity4.SEARCH_PREFIX}${recipe.id}")
              val intent = Intent(Intent.ACTION_VIEW, queryUrl)
              v.context.startActivity(intent)
          } */
    }

    private var onSaveClick: ((FavoriteEntity) -> Unit)? = null

    fun onSaveClickListener(listener: (FavoriteEntity) -> Unit) {
        onSaveClick = listener

    }


}