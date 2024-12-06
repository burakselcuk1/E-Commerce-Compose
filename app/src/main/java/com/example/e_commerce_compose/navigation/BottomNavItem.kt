package com.example.e_commerce_compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Anasayfa", Icons.Default.Home)
    object Category : BottomNavItem("category", "Kategori", Icons.Default.List)
    object Favorites : BottomNavItem("favorites", "Favoriler", Icons.Default.Favorite)
    object Cart : BottomNavItem("cart", "Sepetim", Icons.Default.ShoppingCart)
    object Account : BottomNavItem("account", "Hesabım", Icons.Default.Person)
}