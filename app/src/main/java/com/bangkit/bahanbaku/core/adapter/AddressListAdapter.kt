package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.core.data.remote.response.AddressResultItem
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.utils.addressObjectToString
import com.bangkit.bahanbaku.databinding.ItemAddressBinding

class AddressListAdapter(private val list: List<AddressResultItem>, private val profile: Profile, private val onClick: (addressId: String) -> Unit) :
    RecyclerView.Adapter<AddressListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val address = list[position]
        holder.bind(address)
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(address: AddressResultItem?) {
            if (address != null) {
                binding.tvAddressLabel.text = address.label
                binding.tvNameUser.text = address.receiverName
                binding.tvNoContact.text = address.receiverPhoneNumber

                binding.tvAddress.text = addressObjectToString(address)

                binding.layoutAddress.setOnClickListener {
                    onClick(address.addressId)
                }
            }
        }
    }
}