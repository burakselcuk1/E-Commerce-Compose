package com.example.e_commerce_compose.screens

import CategoryScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_compose.common.AppNavGraph
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
    val selectedTab = remember { mutableStateOf(0) }

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Category,
        BottomNavItem.Favorites,
        BottomNavItem.Cart,
        BottomNavItem.Account
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(navController, bottomNavItems, selectedTab)
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }

}


@Composable
fun BottomNavBar(
    navController: NavController,
    items: List<BottomNavItem>,
    selectedTab: MutableState<Int>
) {
    BottomNavigation(
        modifier = Modifier.navigationBarsPadding(),
        backgroundColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = selectedTab.value == index,
                onClick = {
                    selectedTab.value = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title,style = MaterialTheme.typography.bodySmall) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ECommerceComposeTheme {
        MainScreen()
    }
}