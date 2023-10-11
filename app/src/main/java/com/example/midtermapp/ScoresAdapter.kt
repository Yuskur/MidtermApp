package com.example.midtermapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermapp.databinding.ScoreBinding

class ScoresAdapter(
    val clickListener: (id: Long) -> Unit,
    val deleteClickListener: (id: Long) -> Unit
) : ListAdapter<Score, ScoresAdapter.ScoresViewHolder>(ScoresDiffCallBack()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ScoresViewHolder = ScoresViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int){
        val score = getItem(position)
        holder.bind(score, clickListener, deleteClickListener)
    }

    class ScoresViewHolder(val binding: ScoreBinding) : RecyclerView.ViewHolder(binding.root){

        companion object {
            fun inflateFrom(parent: ViewGroup) : ScoresViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ScoreBinding.inflate(layoutInflater, parent, false)
                return ScoresViewHolder(binding)
            }
        }

        fun bind(
            item: Score,
            clickListener: (id: Long) -> Unit,
            deleteClickListener: (id: Long) -> Unit
        ) {
            binding.score = item
            binding.root.setOnClickListener {clickListener(item.id)}
            binding.imageButton.setOnClickListener{ deleteClickListener(item.id)}
        }
    }
}