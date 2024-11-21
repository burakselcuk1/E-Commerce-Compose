package com.example.e_commerce_compose.screens.productScreen

import com.example.e_commerce_compose.repository.ProductRepository
import com.example.e_commerce_compose.screens.productScreen.model.ProductUiModel
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(categoryId: String): List<ProductUiModel> {
        return repository.fetchProducts(categoryId)
    }
}
