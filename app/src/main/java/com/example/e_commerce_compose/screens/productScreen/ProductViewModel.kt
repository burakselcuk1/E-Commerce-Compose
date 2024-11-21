package com.example.e_commerce_compose.screens.productScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.CategoryV2Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val apolloClient: ApolloClient
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchProducts(categoryId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apolloClient.query(
                    CategoryV2Query(
                        categoryId = "$categoryId",
                        pageSize = 48,
                        pageNumber = 1
                    )
                ).execute()

                val productData = response.data?.categoryV2?.data?.products ?: emptyList()

                _products.value = productData.mapNotNull { product ->
                    product?.let {
                        Product(
                            id = it.id ?: "",
                            name = it.name ?: "",
                            price = 0.0,
                            imageUrl = it.pictures?.firstOrNull()?.imageUrl ?: ""
                        )
                    }
                }
            } catch (e: Exception) {
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String
)
