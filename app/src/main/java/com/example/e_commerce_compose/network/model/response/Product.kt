package com.example.e_commerce_compose.network.model.response

data class ProductResponse(
    val categoryV2: CategoryV2Response?
)

data class CategoryV2Response(
    val data: CategoryDataResponse?
)

data class CategoryDataResponse(
    val name: String?,
    val totalItems: Int?,
    val products: List<Product>?
)

data class Product(
    val id: String,
    val name: String,
    val categories: List<Category>?,
    val price: Price?,
    val pictures: List<Picture>?
)

data class Category(
    val name: String
)

data class Price(
    val priceValue: Double
)

data class Picture(
    val imageUrl: String
)
