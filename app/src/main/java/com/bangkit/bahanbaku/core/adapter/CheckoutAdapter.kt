package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.remote.response.IngredientsItem
import com.bangkit.bahanbaku.core.domain.model.Checkout
import com.bangkit.bahanbaku.databinding.ItemCardIngredientGridBinding
import com.bangkit.bahanbaku.databinding.ItemCheckoutBinding
import com.bumptech.glide.Glide

class CheckoutAdapter(private val list: List<Checkout>) :
    RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCheckoutBinding.inflate(
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

    inner class ViewHolder(val binding: ItemCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: Checkout?) {
            binding.tvTitleCheckout.text = ingredient?.ingredientsName
            binding.tvTotalCheckout.text = "1"
            binding.tvTotalPriceCheckout.text = "Rp20.000"

            Glide.with(itemView.context)
                .load(ingredient?.image)
                .into(binding.imgCheckout)
        }
    }
}