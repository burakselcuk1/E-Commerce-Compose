package com.example.e_commerce_compose.network.model.request

data class GraphQLRequest(
    val query: String,
    val variables: Map<String, Any>? = null
)
