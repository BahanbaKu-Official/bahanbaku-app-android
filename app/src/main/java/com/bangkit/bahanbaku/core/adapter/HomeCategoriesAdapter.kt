package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.utils.DummyCategories
import com.bangkit.bahanbaku.databinding.ItemCardCategorySmallBinding
import com.bumptech.glide.Glide

class HomeCategoriesAdapter(private val list: List<DummyCategories>) : RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardCategorySmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = list[position]
        holder.bind(recipe)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemCardCategorySmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: DummyCategories?) {
            binding.let {
                binding.tvCategoryName.text = category?.name

                Glide.with(itemView.context)
                    .load(category?.img)
                    .into(binding.imgRecipeCategory)

                binding.cardView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        itemView.context.getString(R.string.categories_coming_soon),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}