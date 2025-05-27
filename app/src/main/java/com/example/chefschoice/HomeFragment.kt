package com.example.chefschoice


import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.chefschoice.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    private val viewModel: PopularViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.popularRecyclerView.adapter = PopularListAdapter()
        binding.dessertRecyclerView.adapter = PopularListAdapter()
        binding.quickRecyclerView.adapter = PopularListAdapter()

        binding.button2.setOnClickListener {
            viewModel.trivia.observe(viewLifecycleOwner) {
                binding.foodTrivia.text = it.text
                Log.i("Neariah", "Button clicked.")
            }

        }

        binding.signOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity,MainActivity::class.java)
            startActivity(intent)
            makeText(activity, "Successfully Signed Out", Toast.LENGTH_LONG).show()
        }
        return binding.root
    }
}



