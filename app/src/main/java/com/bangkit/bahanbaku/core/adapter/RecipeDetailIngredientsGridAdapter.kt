package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.remote.response.IngredientsItem
import com.bangkit.bahanbaku.databinding.ItemCardIngredientGridBinding
import com.bumptech.glide.Glide

class RecipeDetailIngredientsGridAdapter(private val list: List<IngredientsItem>) :
    RecyclerView.Adapter<RecipeDetailIngredientsGridAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCardIngredientGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = list[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemCardIngredientGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: IngredientsItem?) {
            binding.let {
                binding.tvCardIngredientTitle.text = ingredient?.ingredient
                binding.tvCardIngredientPrice.text = "Rp20.000"
                binding.tvCardIngredientMeasurement.text = "${ingredient?.amount} butir"

                it.cardItemIngredientGrid.setOnClickListener {
                    ingredient!!.isSelected = !ingredient.isSelected
                    if (ingredient!!.isSelected) {
                        binding.btnAdd.setBackgroundColor(itemView.context.getColor(R.color.primary))
                        binding.btnAdd.text = "Added"
                        binding.btnAdd.setTextColor(itemView.context.getColor(R.color.black))
                    } else {
                        binding.btnAdd.setBackgroundColor(itemView.context.getColor(R.color.black))
                        binding.btnAdd.text = "Add"
                        binding.btnAdd.setTextColor(itemView.context.getColor(R.color.white))
                    }
                }

                Glide.with(itemView.context)
                    .load(ingredient?.imageUrl)
                    .into(binding.imgCardIngredient)
            }
        }
    }
}