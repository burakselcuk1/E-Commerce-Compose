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
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce_compose.ui.theme.ECommerceComposeTheme
import dagger.hilt.android.AndroidEntryPoint

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
    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        "Anasayfa" to Icons.Default.Home,
        "Kategori" to Icons.Default.List,
        "Favoriler" to Icons.Default.Favorite,
        "Sepetim" to Icons.Default.ShoppingCart,
        "Hesabım" to Icons.Default.Person
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.LightGray,
                modifier = Modifier.navigationBarsPadding()
            ) {
                tabs.forEachIndexed { index, tab ->
                    BottomNavigationItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = tab.second,
                                contentDescription = tab.first
                            )
                        },
                        label = {
                            Text(
                                tab.first,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.LightGray
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> FavoritesScreen(Modifier.padding(innerPadding))
            1 -> com.example.e_commerce_compose.screens.mainScreen.MainScreen(Modifier.padding(innerPadding))
            2 -> CategoryScreen(Modifier.padding(innerPadding))
            3 -> CartScreen(Modifier.padding(innerPadding))
            4 -> AccountScreen(Modifier.padding(innerPadding))
        }
    }
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
