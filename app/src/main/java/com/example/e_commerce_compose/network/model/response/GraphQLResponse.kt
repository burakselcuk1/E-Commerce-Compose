package com.example.e_commerce_compose.network.model.response

data class GraphQLResponse<T>(
    val data: T?,
    val errors: List<GraphQLError>?
)

data class GraphQLError(
    val message: String,
    val locations: List<Location>?,
    val path: List<String>?
)
data class MenuByTypeResponse(
    val menuByType: List<Category>
)

data class Location(
    val line: Int,
    val column: Int
)
