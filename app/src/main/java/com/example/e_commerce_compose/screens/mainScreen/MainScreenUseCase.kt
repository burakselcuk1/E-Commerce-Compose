package com.example.e_commerce_compose.screens.mainScreen

import com.example.e_commerce_compose.network.model.response.Product
import com.example.e_commerce_compose.repository.ProductRepository

class MainScreenUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(categoryId: String): List<Product> {
        return repository.getProductsByCategory(categoryId)
    }
}
