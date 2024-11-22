package com.example.e_commerce_compose.screens.productDetailScreen

import com.example.e_commerce_compose.repository.ProductDetailRepository
import com.example.e_commerce_compose.screens.productDetailScreen.model.ProductDetailUiModel
import javax.inject.Inject

class ProductDetailUseCase @Inject constructor(
    private val repository: ProductDetailRepository
) {
    suspend operator fun invoke(productId: String): ProductDetailUiModel? {
        return repository.fetchProductDetail(productId)
    }
}