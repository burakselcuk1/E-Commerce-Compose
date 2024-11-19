package com.example.e_commerce_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerce_compose.ui.theme.ECommerceComposeTheme

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
    val tabs = listOf("Anasayfa", "Kategori", "Favoriler", "Sepetim", "Hesabım")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation {
                tabs.forEachIndexed { index, tab ->
                    BottomNavigationItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(Icons.Filled.Home, contentDescription = tab) },
                        label = { Text(tab) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> HomeScreen(Modifier.padding(innerPadding))
            1 -> CategoryScreen(Modifier.padding(innerPadding))
            2 -> FavoritesScreen(Modifier.padding(innerPadding))
            3 -> CartScreen(Modifier.padding(innerPadding))
            4 -> AccountScreen(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier) {
    Text("Anasayfa", modifier = modifier)
}

@Composable
fun CategoryScreen(modifier: Modifier) {
    Text("Kategori", modifier = modifier)
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
