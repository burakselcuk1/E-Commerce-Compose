package com.example.e_commerce_compose.repository

import com.example.e_commerce_compose.screens.productDetailScreen.model.ProductDetailUiModel

interface ProductDetailRepository {
    suspend fun fetchProductDetail(productId: String): ProductDetailUiModel?
}