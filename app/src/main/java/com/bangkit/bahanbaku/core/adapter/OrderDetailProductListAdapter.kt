package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.remote.response.PaymentProductsItem
import com.bangkit.bahanbaku.databinding.ItemIngredientOrderHistoryBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class OrderDetailProductListAdapter(private val list: List<PaymentProductsItem>) :
    RecyclerView.Adapter<OrderDetailProductListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientOrderHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]
        holder.bind(product)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemIngredientOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: PaymentProductsItem?) {
            binding.let {
                it.tvTitleOrderHistory.text = product?.name

                val formatter = DecimalFormat("#,###")
                val price: Int = product?.price ?: 0
                val formattedNumber = formatter.format(price)

                it.tvTotalPriceOrderHistory.text =
                    itemView.context.getString(R.string.format_currency_rupiah)
                        .format(formattedNumber)

                Glide.with(itemView.context)
                    .load(product?.productImage)
                    .into(it.imgOrderHistory)
            }
        }
    }
}