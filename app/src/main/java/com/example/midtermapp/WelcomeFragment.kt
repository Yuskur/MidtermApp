package com.example.midtermapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.midtermapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding : FragmentWelcomeBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel : WelcomeViewModel
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Welcome Fragment", "Ok we are inside the Welcome Fragment")
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        val view = binding.root

        view.requestFocus()

        //make the viewModel for the fragment
        val application = requireNotNull(this.activity).application
        val dao = MidtermDatabase.getDatabase(application).midtermDao
        val player = WelcomeFragmentArgs.fromBundle(requireArguments()).player
        val score = WelcomeFragmentArgs.fromBundle(requireArguments()).score

        if(player != "no name"){
            binding.welcomeToGame.text = "${player}: $score \n\n Play Again?"
        }

        viewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]
        binding.welcome = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        //don't know if this will work -------------
        //Takes you to the game screen is play game is clicked
        viewModel.playClicked.observe(viewLifecycleOwner, Observer {
            Log.d("play clicked", "entered")
            if(it){
                findNavController().navigate(R.id.action_welcomeFragment_to_gameFragment)
                viewModel.setThingsBack()
            }
        })
        //Takes you to the high scores screen if the view high scores is clicked
        viewModel.viewHighScoresClicked.observe(viewLifecycleOwner, Observer {
            Log.d("view high score clicked", "entered")
            if(it){
                findNavController().navigate(R.id.action_welcomeFragment_to_highScoresFragment)
                viewModel.setThingsBack()
            }
        })

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}