package com.example.chefschoice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chefschoice.network.ComplexSearchData
import com.example.chefschoice.databinding.PopularListBinding
import com.example.chefschoice.databinding.RecipeListBinding
import com.example.chefschoice.network.ComplexSearch

class PopularListAdapter : ListAdapter<ComplexSearchData, PopularListAdapter.PopularViewHolder>(DiffCallback) {

    class PopularViewHolder(
        private var binding:
        PopularListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(complexSearch: ComplexSearchData) {
            binding.photo = complexSearch
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ComplexSearchData>() {
        override fun areItemsTheSame(
            oldItem: ComplexSearchData,
            newItem: ComplexSearchData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ComplexSearchData,
            newItem: ComplexSearchData
        ): Boolean {
            return oldItem.image == newItem.image
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularListAdapter.PopularViewHolder(
            PopularListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val complexSearch = getItem(position)
        holder.bind(complexSearch)

        holder.itemView.setOnClickListener{ v ->
            val intent = Intent( v.context, MainActivity4::class.java)
            intent.putExtra("id", complexSearch.id.toString())
            v.context.startActivity(intent)
            Log.i("Bryan", "Card clicked: " + complexSearch.id)
        }



    }
}