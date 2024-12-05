package com.example.e_commerce_compose.common


import CategoryScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.e_commerce_compose.screens.*
import com.example.e_commerce_compose.screens.accountScreen.AccountScreen
import com.example.e_commerce_compose.screens.cartScreen.CartScreen
import com.example.e_commerce_compose.screens.favoriteScreen.FavoritesScreen
import com.example.e_commerce_compose.screens.homeScreen.HomeScreen
import com.example.e_commerce_compose.screens.productDetailScreen.ProductDetailScreen
import com.example.e_commerce_compose.screens.productScreen.ProductScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }
            )
        }
        composable(BottomNavItem.Category.route) {
            CategoryScreen(
                onCategoryClick = { categoryId ->
                    navController.navigate("products/$categoryId")
                }
            )
        }
        composable(
            route = "productDetail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.navigateUp() }
            )
        }
        composable(BottomNavItem.Favorites.route) {
            FavoritesScreen()
        }
        composable(BottomNavItem.Cart.route) {
            CartScreen()
        }
        composable(BottomNavItem.Account.route) {
            AccountScreen()
        }
        composable(
            route = "products/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
            ProductScreen(
                categoryId = categoryId,
                onBackClick = { navController.navigateUp() },
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }
            )
        }
    }
}
