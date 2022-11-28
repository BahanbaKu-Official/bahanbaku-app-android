package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.data.remote.response.PaymentItem
import com.bangkit.bahanbaku.databinding.ItemChildPaymentMethodBinding
import com.bumptech.glide.Glide

class PaymentMethodListAdapter(private val list: List<PaymentItem>) :
    RecyclerView.Adapter<PaymentMethodListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemChildPaymentMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paymentMethod = list[position]
        holder.bind(paymentMethod)
    }

    override fun getItemCount() = list.size

    class ViewHolder(val binding: ItemChildPaymentMethodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PaymentItem) {
            binding.tvChoosePayment.text = item.paymentName
            Glide.with(itemView.context)
                .load(item.paymentImage)
                .into(binding.imgIconPaymentMethod)
        }
    }
}