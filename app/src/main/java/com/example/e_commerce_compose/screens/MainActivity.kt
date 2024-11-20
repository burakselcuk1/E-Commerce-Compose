package com.example.e_commerce_compose.screens

import CategoryScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.e_commerce_compose.screens.productScreen.ProductScreen
import com.example.e_commerce_compose.ui.theme.ECommerceComposeTheme
import dagger.hilt.android.AndroidEntryPoint

// Bottom Navigation Items
sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Anasayfa", Icons.Default.Home)
    object Category : BottomNavItem("category", "Kategori", Icons.Default.List)
    object Favorites : BottomNavItem("favorites", "Favoriler", Icons.Default.Favorite)
    object Cart : BottomNavItem("cart", "Sepetim", Icons.Default.ShoppingCart)
    object Account : BottomNavItem("account", "Hesabım", Icons.Default.Person)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceComposeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf(0) }

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Favorites,
        BottomNavItem.Cart,
        BottomNavItem.Account
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            val showBottomNav = bottomNavItems.any { it.route == currentRoute }

            if (showBottomNav) {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    bottomNavItems.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            selected = selectedTab == index,
                            onClick = {
                                selectedTab = index
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    item.title,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            selectedContentColor = MaterialTheme.colorScheme.primary,
                            unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(Modifier)
            }

            composable(BottomNavItem.Category.route) {
                CategoryScreen(
                    modifier = Modifier,
                    onCategoryClick = { categoryId ->
                        navController.navigate("products/$categoryId")
                    }
                )
            }

            composable(BottomNavItem.Favorites.route) {
                FavoritesScreen(Modifier)
            }

            composable(BottomNavItem.Cart.route) {
                CartScreen(Modifier)
            }

            composable(BottomNavItem.Account.route) {
                AccountScreen(Modifier)
            }

            composable(
                route = "products/{categoryId}",
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
                ProductScreen(
                    categoryId = categoryId,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier) {
    Text("Anasayfa", modifier = modifier)
}

@Composable
fun FavoritesScreen(modifier: Modifier) {
    Text("Favoriler", modifier = modifier)
}

@Composable
fun CartScreen(modifier: Modifier) {
    Text("Sepetim", modifier = modifier)
}

@Composable
fun AccountScreen(modifier: Modifier) {
    Text("Hesabım", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ECommerceComposeTheme {
        MainScreen()
    }
}