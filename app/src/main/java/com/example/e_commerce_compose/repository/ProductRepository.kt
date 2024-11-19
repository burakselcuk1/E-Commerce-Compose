package com.example.e_commerce_compose.repository

import com.example.e_commerce_compose.network.model.response.Product

interface ProductRepository {
    suspend fun getProductsByCategory(categoryId: String): List<Product>
}
