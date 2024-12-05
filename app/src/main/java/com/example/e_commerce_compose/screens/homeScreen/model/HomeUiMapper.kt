package com.example.e_commerce_compose.screens.homeScreen.model

import com.example.CategoryV2Query
import com.example.e_commerce_compose.screens.productScreen.model.ProductUiModel

class HomeUiMapper {
    fun mapToProduct(product: CategoryV2Query.Product): ProductUiModel {
        return ProductUiModel(
            id = product.id ?: "",
            name = product.name ?: "",
            price = 0.0,
            imageUrl = product.pictures?.firstOrNull()?.imageUrl ?: ""
        )
    }
}