package com.example.e_commerce_compose.screens.productScreen.model

import com.example.CategoryV2Query

class ProductUiMapper {
    fun mapToProduct(product: CategoryV2Query.Product): ProductUiModel {
        return ProductUiModel(
            id = product.id ?: "",
            name = product.name ?: "",
            price = 0.0,
            imageUrl = product.pictures?.firstOrNull()?.imageUrl ?: ""
        )
    }
}