package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.remote.response.OrderHistoryItem
import com.bangkit.bahanbaku.databinding.ItemCardOrderHistoryBinding

class OrderHistoryAdapter(private val list: List<OrderHistoryItem>) : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {
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
        fun bind(product: OrderHistoryItem?) {
            binding.let {
                val recipe = product?.recipe

                if (recipe != null) {
                    binding.tvTitleCheckoutRecipe.text = recipe.title
                }

                if (product != null) {
                    binding.tvOrderStatus.text = product.status.uppercase()
                    binding.rvCheckout.apply {
                        adapter = OrderHistoryItemAdapter(product.products)
                        layoutManager = LinearLayoutManager(itemView.context)
                    }
                }

                it.cardOrderHistory.setOnClickListener {
                    Toast.makeText(itemView.context, itemView.context.getString(R.string.coming_soon_string), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}