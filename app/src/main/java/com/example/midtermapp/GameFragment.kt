package com.example.midtermapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.midtermapp.databinding.FragmentGameBinding
import com.example.midtermapp.databinding.FragmentGameScreenBinding
import kotlin.random.Random

class GameFragment : Fragment() {

    private var _binding : FragmentGameBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = MidtermDatabase.getDatabase(application).midtermDao
        val viewModelFactory = GameViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]

        //Generates the random number
        viewModel.randomNumber = Random.nextInt(1, 100)
        binding.lifecycleOwner = viewLifecycleOwner

        /*
        Upon getting the guess right it will navigate you back to the welcome page
         */
        viewModel.guessedRight.observe(viewLifecycleOwner, Observer {right ->
            if(right){
                Log.d("guessed Right", "Going to the welcome fragment")
                val player = viewModel.name
                val score = viewModel.gameScore
                val action = GameFragmentDirections.actionGameFragmentToWelcomeFragment(player, score)
                this.findNavController().navigate(action)
                viewModel.setBack()
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}