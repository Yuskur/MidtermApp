package com.example.midtermapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.midtermapp.databinding.FragmentHighScoresBinding

class HighScoresFragment : Fragment() {

    private var _binding : FragmentHighScoresBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHighScoresBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = MidtermDatabase.getDatabase(application).midtermDao
        val viewModelFactory = HighScoresViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[HighScoresViewModel::class.java]

        binding.highScore = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        fun yesClicked(id: Long){
            viewModel.delete(id)
        }

        fun delete(id : Long){
            ConfirmDeleteDialogFragment(id,::yesClicked).show(childFragmentManager,
                ConfirmDeleteDialogFragment.TAG)
        }

        val adapter = ScoresAdapter(::delete)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        //adds the changes of the scores to the recycler view
        viewModel.scores.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("Scores Observer", "Entered Not Null")
                adapter.submitList(it)
            }
            if(it.isEmpty()){
                viewModel.enableVisibility()
                Log.d("State of visibility: enabled", "${viewModel.textVisibility.get()} and ${viewModel.recyclerViewVisibility.get()}")
            }
            else{
                viewModel.disableVisibility()
                Log.d("State of visibility: disabled", "${viewModel.textVisibility.get()} and ${viewModel.recyclerViewVisibility.get()}")
            }
        })
        return view
    }

}