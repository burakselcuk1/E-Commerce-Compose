package com.example.e_commerce_compose.screens.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce_compose.network.model.response.Product

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val products by viewModel.products

    Column(modifier = Modifier.fillMaxSize()) {
        if (products.isEmpty()) {
            Text(text = "Yükleniyor...") // Veri yüklenirken gösterilecek
        } else {
            LazyColumn {
                items(products) { product ->
                    ProductItem(product = product)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Text(text = product.name)
    Text(text = "${product.price} TL")
}
