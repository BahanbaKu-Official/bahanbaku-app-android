package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.data.local.entity.IngredientEntity
import com.bangkit.bahanbaku.databinding.ItemIngredientsBinding

class DialogIngredientsAdapter(private val list: ArrayList<IngredientEntity>) :
    RecyclerView.Adapter<DialogIngredientsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = list[position]
        holder.bind(ingredient)
    }

    override fun getItemCount() = list.size

    class ViewHolder(val binding: ItemIngredientsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IngredientEntity) {
            binding.tvIngredient.text = item.ingredient
        }
    }
}