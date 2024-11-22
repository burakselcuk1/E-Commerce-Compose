package com.example.e_commerce_compose.screens.productDetailScreen.model

import com.example.ProductDetailQuery


class ProductDetailUiMapper  {
    fun mapToProductDetail(product: ProductDetailQuery.Product): ProductDetailUiModel {
        return ProductDetailUiModel(
            id = product.id ?: "",
            name = product.name ?: "",
            fullDescription = product.fullDescription ?: "",
            imageUrl = product.pictureModels?.firstOrNull()?.imageUrl ?: ""
        )
    }
}