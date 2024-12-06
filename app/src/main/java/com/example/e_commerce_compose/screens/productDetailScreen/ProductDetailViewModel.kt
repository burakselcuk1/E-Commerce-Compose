package com.example.e_commerce_compose.screens.productDetailScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.BaseViewModel
import com.example.core.LoadingManager
import com.example.e_commerce_compose.screens.productDetailScreen.model.ProductDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productDetailUseCase: ProductDetailUseCase,
    loadingManager: LoadingManager
) : BaseViewModel(loadingManager) {

    private val _productDetail = MutableStateFlow<ProductDetailUiModel?>(null)
    val productDetail: StateFlow<ProductDetailUiModel?> = _productDetail.asStateFlow()

    fun fetchProductDetail(productId: String) {
        launchWithLoading(
            scope = viewModelScope,
            block = {
                productDetailUseCase(productId)
            },
            onSuccess = { detail ->
                _productDetail.value = detail
            },
            onError = { e ->
                Log.e("ProductDetailViewModel", "Error fetching product detail", e)
                _productDetail.value = null
            }
        )
    }
}