package com.example.e_commerce_compose.screens.productDetailScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_compose.screens.productDetailScreen.model.ProductDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailUseCase: ProductDetailUseCase
) : ViewModel() {
    private val _productDetail = MutableStateFlow<ProductDetailUiModel?>(null)
    val productDetail: StateFlow<ProductDetailUiModel?> = _productDetail.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchProductDetail(productId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _productDetail.value = productDetailUseCase(productId)
            } catch (e: Exception) {
                Log.e("ProductDetailViewModel", "Error fetching product detail", e)
                _productDetail.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}

