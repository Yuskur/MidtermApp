package com.example.midtermapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.midtermapp.databinding.FragmentGameBinding
import com.example.midtermapp.databinding.FragmentGameScreenBinding
import kotlin.random.Random

class GameScreenFragment : Fragment() {

    private var _binding : FragmentGameScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        //add the view of the xml file
        val application = requireNotNull(this.activity).application
        //making an instance of the dao
        val dao = MidtermDatabase.getDatabase(application).midtermDao
        /*
        making an instance of the GameViewModel with requireParentFragment because GameScreenFragment
        is a nested fragment inside of the GameFragment
         */
        val viewModelFactory = GameViewModelFactory(dao)
        val viewModel = ViewModelProvider(requireParentFragment(), viewModelFactory)[GameViewModel::class.java]

        //data binding the game screen view with the viewModel to pass data
        binding.gameScreen = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        /*
        When the user makes guess it will compare their answer with the random number
        using the checkAns method. If right it will set guessed right to true to navigate you back
        to the welcome screen. If you do not end up getting the guess right it will tell you
        if you next guess needs to be higher or lower
         */
        viewModel.guessTaken.observe(viewLifecycleOwner, Observer {
            if(it){
                val check = viewModel.checkAns()
                if(check){
                    viewModel.onGuessedRight()
                } else {
                    //Make an object of the media player to play the sound
                    val mediaPlayer : MediaPlayer
                    if(viewModel.guess.get()!!.toInt() < viewModel.randomNumber){
                        //Plays wrong sound if the user gets it wrong
                        mediaPlayer = MediaPlayer.create(context, R.raw.buzz)
                        mediaPlayer.start()
                        Toast.makeText(requireContext(), "Higher", Toast.LENGTH_LONG).show()
                    } else{
                        //Plays wrong sound if the user gets it wrong
                        mediaPlayer = MediaPlayer.create(context, R.raw.buzz)
                        mediaPlayer.start()
                        Toast.makeText(requireContext(), "Lower", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        /*
        If the user has not entered a name yet and they try and check their answer it will
        display a toast indicating that they do not have a name
         */
        viewModel.noName.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(requireContext(), "NO NAME", Toast.LENGTH_SHORT).show()
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