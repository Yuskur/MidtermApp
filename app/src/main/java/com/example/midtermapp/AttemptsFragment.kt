package com.example.midtermapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.midtermapp.databinding.FragmentAttemptsBinding

//No need to do anything in this fragment
class AttemptsFragment : Fragment() {

    private var _binding : FragmentAttemptsBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAttemptsBinding.inflate(inflater, container, false)
        val view = binding.root

        //getting the application to make the dao
        val application = requireNotNull(this.activity).application
        val dao = MidtermDatabase.getDatabase(application).midtermDao
        /*
        making an instance of the Game ViewModel and requiring parent fragment because it is a
        shared viewModel with the GameFragment and AttemptsFragment is nested in the GameFragment
         */
        val viewModelFactory = GameViewModelFactory(dao)
        val viewModel = ViewModelProvider(requireParentFragment(), viewModelFactory)[GameViewModel::class.java]

        //data binding the attempts xml file to the viewModel so data can be shared
        binding.attempts = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }
}