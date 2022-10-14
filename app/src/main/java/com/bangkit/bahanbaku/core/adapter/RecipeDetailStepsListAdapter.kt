package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.data.remote.response.StepsItem
import com.bangkit.bahanbaku.databinding.ItemCardStepListBinding

class RecipeDetailStepsListAdapter(private val list: List<StepsItem>) :
    RecyclerView.Adapter<RecipeDetailStepsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCardStepListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = list[position]
        holder.bind(recipe, position)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemCardStepListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(step: StepsItem?, position: Int) {
            binding.let {
                binding.tvStep.text = step?.step
                val stepPosition = (position + 1).toString()
                binding.tvNumber.text = stepPosition
            }
        }
    }
}