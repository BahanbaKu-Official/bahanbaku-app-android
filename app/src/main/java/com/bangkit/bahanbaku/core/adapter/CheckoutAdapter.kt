package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.domain.model.Product
import com.bangkit.bahanbaku.core.utils.capitalize
import com.bangkit.bahanbaku.databinding.ItemCheckoutBinding
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class CheckoutAdapter(private val list: List<Product>, val onItemClicked: () -> Unit) :
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
        holder.bind(recipe, position)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product?, position: Int) {
            binding.tvTitleCheckout.text = capitalize(product?.name ?: "")

            val formatter = DecimalFormat("#,###")
            val price: Int = product?.price ?: 0
            val formattedNumber = formatter.format(price)

            binding.tvTotalPriceCheckout.text =
                itemView.context.getString(R.string.format_currency_rupiah).format(formattedNumber)
            binding.tvTotalCheckout.text = product?.quantity.toString()

            binding.btnCardPlus.setOnClickListener {
                if (product?.stock!! > product.quantity) {
                    product.quantity++
                    binding.tvTotalCheckout.text = product.quantity.toString()
                    onItemClicked.invoke()
                } else {
                    Toast.makeText(
                        itemView.context,
                        itemView.context.getString(R.string.quantity_cannot_exceed_stock),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            binding.btnCardMinus.setOnClickListener {
                if (product?.quantity!! > 1) {
                    product.quantity--
                    onItemClicked.invoke()
                    binding.tvTotalCheckout.text = product.quantity.toString()
                }
            }

            Glide.with(itemView.context)
                .load(product?.productImage)
                .into(binding.imgCheckout)
        }
    }
}