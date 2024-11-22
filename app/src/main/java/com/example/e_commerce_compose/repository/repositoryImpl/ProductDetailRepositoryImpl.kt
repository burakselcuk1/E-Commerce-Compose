package com.example.e_commerce_compose.repository.repositoryImpl

import com.apollographql.apollo3.ApolloClient
import com.example.ProductDetailQuery
import com.example.e_commerce_compose.repository.ProductDetailRepository
import com.example.e_commerce_compose.screens.productDetailScreen.model.ProductDetailUiMapper
import com.example.e_commerce_compose.screens.productDetailScreen.model.ProductDetailUiModel
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val uiMapper: ProductDetailUiMapper
) : ProductDetailRepository {
    override suspend fun fetchProductDetail(productId: String): ProductDetailUiModel? {
        val response = apolloClient.query(
            ProductDetailQuery(productId)
        ).execute()

        return response.data?.product?.let { product ->
            uiMapper.mapToProductDetail(product)
        }
    }
}