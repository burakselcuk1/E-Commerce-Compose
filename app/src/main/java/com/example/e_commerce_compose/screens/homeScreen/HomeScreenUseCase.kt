package com.example.e_commerce_compose.screens.homeScreen

import com.example.e_commerce_compose.repository.HomeRepository
import com.example.e_commerce_compose.screens.productScreen.model.ProductUiModel
import javax.inject.Inject

class HomeScreenUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(categoryId: String, page: Int): List<ProductUiModel> {
        return repository.fetchProducts(categoryId, page)
    }
}
