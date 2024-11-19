package com.example.e_commerce_compose.repository

import com.example.e_commerce_compose.network.model.response.Category

interface CategoryRepository {

    suspend fun getCategories(): List<Category>

}