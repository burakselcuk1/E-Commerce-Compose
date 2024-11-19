package com.example.e_commerce_compose.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerce_compose.network.model.response.Category
import com.example.e_commerce_compose.screens.categoryScreen.CategoryViewModel

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel()) {

    val categories = viewModel.categories.value

    LazyColumn {
        items(categories) { category ->
            CategoryItem(category)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column {
        Text(text = category.name)
        category.name?.forEach { subcategory ->
            Text(text = "  ${subcategory}")
        }
    }
}
