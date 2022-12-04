package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.remote.response.IngredientsItem
import com.bangkit.bahanbaku.core.utils.capitalize
import com.bangkit.bahanbaku.databinding.ItemCardIngredientGridBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

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
                binding.tvCardIngredientTitle.text = capitalize(ingredient?.ingredient ?: "")

                val formatter = DecimalFormat("#,###")
                val price: Int = ingredient?.products?.price ?: 0
                val formattedNumber = formatter.format(price)

                binding.tvCardIngredientPrice.text =
                    itemView.context.getString(R.string.format_currency_rupiah)
                        .format(formattedNumber)

                if (ingredient?.amount?.toInt() == 0) {
                    binding.tvCardIngredientMeasurement.text = ingredient.unit
                } else {
                    binding.tvCardIngredientMeasurement.text =
                        itemView.context.getString(R.string.format_measurement)
                            .format(ingredient?.amount?.toInt(), ingredient?.unit)
                }

                if (ingredient?.products != null) {
                    updateUiState(ingredient, binding, itemView)

                    it.btnAdd.setOnClickListener {
                        ingredient.isSelected = !ingredient.isSelected
                        updateUiState(ingredient, binding, itemView)
                    }

                    if (ingredient.products.stock <= 0 || ingredient.products.price <= 0) {
                        binding.btnAdd.setBackgroundColor(itemView.context.getColor(R.color.placeholder_gray))
                        binding.btnAdd.isClickable = false
                        binding.btnAdd.text = itemView.context.getString(R.string.unavailable)
                        binding.tvCardIngredientPrice.text =
                            itemView.context.getString(R.string.unavailable)
                    }
                } else {
                    binding.btnAdd.setBackgroundColor(itemView.context.getColor(R.color.placeholder_gray))
                    binding.btnAdd.isClickable = false
                    binding.btnAdd.text = itemView.context.getString(R.string.unavailable)
                    binding.tvCardIngredientPrice.text =
                        itemView.context.getString(R.string.unavailable)
                }

                Glide.with(itemView.context)
                    .load(ingredient?.imageUrl)
                    .into(binding.imgCardIngredient)
            }
        }
    }

    private fun updateUiState(
        ingredient: IngredientsItem,
        binding: ItemCardIngredientGridBinding,
        itemView: View
    ) {
        if (ingredient.products.stock > 0 && ingredient.products.price > 0) {
            if (ingredient.isSelected) {
                binding.btnAdd.setBackgroundColor(itemView.context.getColor(R.color.primary))
                binding.btnAdd.text = itemView.context.getString(R.string.added)
                binding.btnAdd.setTextColor(itemView.context.getColor(R.color.black))
            } else {
                binding.btnAdd.setBackgroundColor(itemView.context.getColor(R.color.black))
                binding.btnAdd.text = itemView.context.getString(R.string.add)
                binding.btnAdd.setTextColor(itemView.context.getColor(R.color.white))
            }
        }
    }
}