package com.example.e_commerce_compose.repository.repositoryImpl

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.GetCategoriesQuery
import com.example.e_commerce_compose.network.model.request.GraphQLRequest
import com.example.e_commerce_compose.network.model.response.Category
import com.example.e_commerce_compose.network.model.response.CategoryV2Response
import com.example.e_commerce_compose.network.model.response.GraphQLResponse
import com.example.e_commerce_compose.network.model.response.MenuByTypeResponse
import com.example.e_commerce_compose.network.model.response.ProductResponse
import com.example.e_commerce_compose.network.service.GraphQLService
import com.example.e_commerce_compose.repository.CategoryRepository
import com.example.e_commerce_compose.screens.categoryScreen.CategoryUiMapper
import com.example.e_commerce_compose.screens.categoryScreen.model.CategoryWithSubCategories
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apolloClient: ApolloClient,
    private val uiMapper: CategoryUiMapper
) : CategoryRepository {
    override suspend fun fetchCategories(): List<CategoryWithSubCategories> {
        val response = apolloClient.query(GetCategoriesQuery()).execute()
        val menuItems = response.data?.menuByType?.filterNotNull() ?: emptyList()

        return uiMapper.mapToCategories(menuItems)
    }
}