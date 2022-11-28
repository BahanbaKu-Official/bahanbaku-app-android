package com.bangkit.bahanbaku.core.domain.model

data class ProductsData(
    val products: List<ProductJSONFormat>
)

data class ProductJSONFormat(
    val id: String,
    val quantity: Int,
)