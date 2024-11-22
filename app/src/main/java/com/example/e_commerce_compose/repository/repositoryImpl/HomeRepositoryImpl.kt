package com.example.e_commerce_compose.repository.repositoryImpl

import com.apollographql.apollo3.ApolloClient
import com.example.CategoryV2Query
import com.example.e_commerce_compose.repository.HomeRepository
import com.example.e_commerce_compose.repository.ProductRepository
import com.example.e_commerce_compose.screens.mainScreen.model.HomeUiMapper
import com.example.e_commerce_compose.screens.productScreen.model.ProductUiMapper
import com.example.e_commerce_compose.screens.productScreen.model.ProductUiModel
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val uiMapper: HomeUiMapper
) : HomeRepository {
    override suspend fun fetchProducts(categoryId: String, page: Int): List<ProductUiModel> {
        val response = apolloClient.query(
            CategoryV2Query(
                categoryId = categoryId,
                pageSize = 48,
                pageNumber = page
            )
        ).execute()

        val productData = response.data?.categoryV2?.data?.products ?: emptyList()
        return productData.mapNotNull { product ->
            product?.let {
                uiMapper.mapToProduct(it)
            }
        }
    }
}
