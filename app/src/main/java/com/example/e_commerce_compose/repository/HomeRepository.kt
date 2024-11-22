package com.example.e_commerce_compose.repository

import com.example.e_commerce_compose.screens.productScreen.model.ProductUiModel

interface HomeRepository {
    suspend fun fetchProducts(categoryId: String, page: Int): List<ProductUiModel>
}