package com.bangkit.bahanbaku.core.utils

import com.bangkit.bahanbaku.core.data.remote.response.Address
import com.bangkit.bahanbaku.core.data.remote.response.AddressResultItem
import com.bangkit.bahanbaku.core.domain.model.AddressInput
import java.text.DecimalFormat
import java.util.*

fun capitalize(str: String): String {
    return str.trim().split("\\s+".toRegex()).joinToString(" ") { it.capitalize(Locale.ROOT) }
}

fun addressObjectToString(address: AddressInput): String {
    val stringBuilder = StringBuilder()
    if (address.street != "") {
        stringBuilder.append(address.street)
    }
    if (address.district != "") {
        stringBuilder.append(", ${address.district}")
    }
    if (address.city != "") {
        stringBuilder.append(", ${address.city}")
    }
    if (address.province != "") {
        stringBuilder.append(", ${address.province}")
    }
    if (address.zipCode > 0) {
        stringBuilder.append(" ${address.zipCode}")
    }

    return stringBuilder.toString()
}

fun addressObjectToString(address: Address): String {
    val stringBuilder = StringBuilder()
    if (address.street != "") {
        stringBuilder.append(address.street)
    }
    if (address.district != "") {
        stringBuilder.append(", ${address.district}")
    }
    if (address.city != "") {
        stringBuilder.append(", ${address.city}")
    }
    if (address.province != "") {
        stringBuilder.append(", ${address.province}")
    }
    if (address.zipCode > 0) {
        stringBuilder.append(" ${address.zipCode}")
    }

    return stringBuilder.toString()
}

fun currencyIntToString(number: Int): String {
    val formatter = DecimalFormat("#,###")
    val price: Int = number

    return formatter.format(price)
}

fun addressObjectToString(address: AddressResultItem): String {
    val stringBuilder = StringBuilder()
    if (address.street != "") {
        stringBuilder.append(address.street)
    }
    if (address.district != "") {
        stringBuilder.append(", ${address.district}")
    }
    if (address.city != "") {
        stringBuilder.append(", ${address.city}")
    }
    if (address.province != "") {
        stringBuilder.append(", ${address.province}")
    }
    if (address.zipCode > 0) {
        stringBuilder.append(" ${address.zipCode}")
    }

    return stringBuilder.toString()
}