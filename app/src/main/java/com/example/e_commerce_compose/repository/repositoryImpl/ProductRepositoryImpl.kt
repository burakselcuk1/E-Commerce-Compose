package com.example.e_commerce_compose.repository.repositoryImpl

import android.util.Log
import com.example.e_commerce_compose.network.model.request.GraphQLRequest
import com.example.e_commerce_compose.network.model.response.Product
import com.example.e_commerce_compose.network.model.response.ProductResponse
import com.example.e_commerce_compose.network.service.GraphQLService
import com.example.e_commerce_compose.repository.ProductRepository

class ProductRepositoryImpl(
    private val graphQLService: GraphQLService
) : ProductRepository {
    override suspend fun getProductsByCategory(categoryId: String): List<Product> {
        val query = """
            query CategoryV2(${"$"}categoryId: String!, ${"$"}pageNumber: Int!, ${"$"}pageSize: Int!) {
                categoryV2(
                    categoryId: ${"$"}categoryId,
                    command: {
                        orderBy: 0,
                        pageNumber: ${"$"}pageNumber,
                        pageSize: ${"$"}pageSize,
                        prepareSpecsFilters: true,
                        pagePath: "https://hcapi.sch.prod.hebiar.com/graphql/categoryv2?categoryId=${"$"}categoryId&includeDocuments=true&includeFacets=true",
                        selectedSpecFilter: []
                    }
                ) {
                    data {
                        name
                        totalItems
                        products {
                            id
                            name
                            categories {
                                name
                            }
                            price {
                                priceValue
                            }
                            pictures {
                                imageUrl
                            }
                        }
                    }
                }
            }
        """.trimIndent()

        val variables = mapOf(
            "categoryId" to categoryId,
            "pageNumber" to 1, // Varsayılan olarak 1. sayfa
            "pageSize" to 10   // Varsayılan olarak 20 ürün
        )
        val request = GraphQLRequest(query = query, variables = variables)

       // val response = graphQLService.executeQuery<ProductResponse>(request)
        //Log.d("GraphQLBurakReis", response.toString())

       // return response.data?.categoryV2?.data?.products ?: emptyList()
        return emptyList()
    }
}
