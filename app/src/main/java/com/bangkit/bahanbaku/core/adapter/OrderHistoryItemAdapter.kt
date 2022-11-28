package com.bangkit.bahanbaku.core.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.data.remote.response.ProductHistoryItem
import com.bangkit.bahanbaku.core.domain.model.Recipe
import com.bangkit.bahanbaku.databinding.ItemCardOrderHistoryBinding
import com.bangkit.bahanbaku.databinding.ItemCardRecipeLargeBinding
import com.bangkit.bahanbaku.presentation.detail.DetailActivity
import com.bumptech.glide.Glide

class OrderHistoryItemAdapter(private val list: List<ProductHistoryItem>) : RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCardOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]
        holder.bind(product)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemCardOrderHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: ProductHistoryItem?) {
            binding.let {

            }
        }
    }
}