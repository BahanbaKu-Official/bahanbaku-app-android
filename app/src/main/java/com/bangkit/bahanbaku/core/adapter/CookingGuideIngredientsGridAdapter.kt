package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.data.remote.response.IngredientsItem
import com.bangkit.bahanbaku.databinding.ItemCardIngredientCookingGuideGridBinding
import com.bumptech.glide.Glide

class CookingGuideIngredientsGridAdapter(private val data: List<IngredientsItem>) :
    RecyclerView.Adapter<CookingGuideIngredientsGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCardIngredientCookingGuideGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = data[position]
        holder.bind(ingredient)
    }

    override fun getItemCount() = data.size

    inner class ViewHolder(val binding: ItemCardIngredientCookingGuideGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: IngredientsItem?) {
            binding.let {
                binding.tvCardIngredientTitle.text = ingredient?.ingredient
                binding.tvCardIngredientMeasurement.text = ingredient?.amount.toString()

                Glide.with(itemView.context)
                    .load(ingredient?.imageUrl)
                    .into(binding.imgCardIngredient)
            }
        }
    }
}