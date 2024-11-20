package com.example.e_commerce_compose.network.service

import com.example.e_commerce_compose.network.model.request.GraphQLRequest
import com.example.e_commerce_compose.network.model.response.GraphQLResponse
import com.example.e_commerce_compose.network.model.response.ProductResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GraphQLService {
        @POST("graphql")
        suspend fun executeQuery(@Body request: GraphQLRequest): Map<String, Any>
    }

