package com.example.e_commerce_compose.repository.repositoryImpl

import android.util.Log
import com.example.e_commerce_compose.network.model.request.GraphQLRequest
import com.example.e_commerce_compose.network.model.response.Category
import com.example.e_commerce_compose.network.model.response.CategoryV2Response
import com.example.e_commerce_compose.network.model.response.ProductResponse
import com.example.e_commerce_compose.network.service.GraphQLService
import com.example.e_commerce_compose.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val graphQLService: GraphQLService
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        val query = """ 
        query GetCategories { 
            categories { 
                id 
                name 
            } 
        } 
    """.trimIndent()


        val request = GraphQLRequest(query = query, variables = null)
        val response = graphQLService.executeQuery<List<Category>>(request)
        Log.d("GraphQLBurakReis", response.toString())

        return response.data ?: emptyList()
    }
}
