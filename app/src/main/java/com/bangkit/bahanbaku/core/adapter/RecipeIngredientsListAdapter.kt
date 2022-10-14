package com.bangkit.bahanbaku.core.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.data.remote.response.SuppliersItem
import com.bangkit.bahanbaku.databinding.ItemEcommBinding

class RecipeIngredientsListAdapter(private val list: List<String>) : RecyclerView.Adapter<RecipeIngredientsListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemEcommBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SuppliersItem) {
            binding.tvStoreName.text = item.supplierName
            binding.tvShippingCost.text = "Rp.${item.shippingCost}"

            binding.rvIngredients.apply {
                adapter = IngredientsItemAdapter(item.products)
                layoutManager = LinearLayoutManager(this.context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}