package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.data.remote.response.IngredientsItem
import com.bangkit.bahanbaku.databinding.ItemCardIngredientListBinding
import com.bumptech.glide.Glide

class RecipeDetailIngredientsListAdapter(private val list: List<IngredientsItem>) :
    RecyclerView.Adapter<RecipeDetailIngredientsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCardIngredientListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = list[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemCardIngredientListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: IngredientsItem?) {
            binding.let {
                binding.tvCardIngredientTitle.text = ingredient?.ingredient
                binding.tvCardIngredientPrice.text = "Rp20.000"
                binding.tvCardIngredientMeasurement.text = "${ingredient?.amount} butir"

                Glide.with(itemView.context)
                    .load(ingredient?.imageUrl)
                    .into(binding.imgCardIngredient)
            }
        }
    }
}