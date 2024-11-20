package com.example.e_commerce_compose.repository.repositoryImpl

import android.util.Log
import com.example.e_commerce_compose.network.model.request.GraphQLRequest
import com.example.e_commerce_compose.network.model.response.Category
import com.example.e_commerce_compose.network.model.response.CategoryV2Response
import com.example.e_commerce_compose.network.model.response.GraphQLResponse
import com.example.e_commerce_compose.network.model.response.MenuByTypeResponse
import com.example.e_commerce_compose.network.model.response.ProductResponse
import com.example.e_commerce_compose.network.service.GraphQLService
import com.example.e_commerce_compose.repository.CategoryRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryRepositoryImpl(
    private val graphQLService: GraphQLService
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        val query = """
    query FetchCategoriesQuery {
        menuByType(type: "Main Menu") {
            id
            name
            icon
            menuId
            parentMenuId
            type
        }
    }
    """.trimIndent()

        val request = GraphQLRequest(query = query, variables = null)
        val responseMap = graphQLService.executeQuery(request)

        // JSON'u manuel olarak parse et
        val gson = Gson()
        val type = object : TypeToken<GraphQLResponse<MenuByTypeResponse>>() {}.type
        val responseJson = gson.toJson(responseMap)
        val response = gson.fromJson<GraphQLResponse<MenuByTypeResponse>>(responseJson, type)

        Log.d("GraphQLBurakReis", response.toString())

        return response.data?.menuByType ?: emptyList()
    }

}
