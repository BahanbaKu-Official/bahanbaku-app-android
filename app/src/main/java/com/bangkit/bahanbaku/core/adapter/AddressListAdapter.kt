package com.bangkit.bahanbaku.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bahanbaku.R
import com.bangkit.bahanbaku.core.data.remote.response.AddressResultItem
import com.bangkit.bahanbaku.core.domain.model.Profile
import com.bangkit.bahanbaku.core.utils.addressObjectToString
import com.bangkit.bahanbaku.databinding.ItemAddressBinding
import com.bangkit.bahanbaku.databinding.ItemCardIngredientGridBinding

class AddressListAdapter(private val list: List<AddressResultItem>, private val profile: Profile) :
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
                binding.tvNameUser.text = itemView.context.getString(R.string.format_name)
                    .format(profile.firstName, profile.lastName)

                binding.tvAddress.text = addressObjectToString(address)
            }
        }
    }
}