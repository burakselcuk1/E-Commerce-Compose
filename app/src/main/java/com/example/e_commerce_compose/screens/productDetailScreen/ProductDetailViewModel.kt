package com.example.e_commerce_compose.screens.productDetailScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.example.ProductDetailQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val apolloClient: ApolloClient
) : ViewModel() {
    private val _productDetail = MutableStateFlow<ProductDetail?>(null)
    val productDetail: StateFlow<ProductDetail?> = _productDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchProductDetail(productId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apolloClient.query(
                    ProductDetailQuery(productId)
                ).execute()

                val product = response.data?.product

                Log.d("bozoreis",response.data.toString())

                _productDetail.value = product?.let {
                    ProductDetail(
                        id = it.id ?: "",
                        name = it.name ?: "",
                        fullDescription = it.fullDescription ?: "",
                        imageUrl = it.pictureModels?.firstOrNull()?.imageUrl ?: ""
                    )
                }
            } catch (e: Exception) {
                _productDetail.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class ProductDetail(
    val id: String,
    val name: String,
    val fullDescription: String,
    val imageUrl: String
)
