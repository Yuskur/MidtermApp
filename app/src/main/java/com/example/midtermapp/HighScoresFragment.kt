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
        //Making an instance of dao
        val dao = MidtermDatabase.getDatabase(application).midtermDao
        //Making an instance of the HighScoresViewModel
        val viewModelFactory = HighScoresViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[HighScoresViewModel::class.java]

        //data binds the high scores xml file to send and retrieve data accordingly
        binding.highScore = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //When yes is clicked it will call the delete method inside of the HighScoresViewModel
        fun yesClicked(id: Long){
            viewModel.delete(id)
        }

        //When delete method is triggered it will cause a Dialogue fragment to display
        fun delete(id : Long){
            ConfirmDeleteDialogFragment(id,::yesClicked).show(childFragmentManager,
                ConfirmDeleteDialogFragment.TAG)
        }

        //Making an instance of the adapter
        val adapter = ScoresAdapter(::delete)
        //Making an instance of the recycler view
        val recyclerView = binding.recyclerView

        //Require context for the layout manager to display the view the recycler view puts out
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //binds the recycler view to the adapter
        binding.recyclerView.adapter = adapter

        //adds the changes of the scores to the recycler view
        viewModel.scores.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("Scores Observer", "Entered Not Null")
                adapter.submitList(it)
            }
            //If there is no high scores inside of the database it will enable the text "No high scores yet"
            if(it.isEmpty()){
                viewModel.enableVisibility()
                Log.d("State of visibility: enabled", "${viewModel.textVisibility.get()} and ${viewModel.recyclerViewVisibility.get()}")
            }
            //It will disable the "No high scores yet" text view if there are high scores
            else{
                viewModel.disableVisibility()
                Log.d("State of visibility: disabled", "${viewModel.textVisibility.get()} and ${viewModel.recyclerViewVisibility.get()}")
            }
        })
        return view
    }

}