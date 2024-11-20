import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.e_commerce_compose.screens.categoryScreen.CategoryViewModel
import com.example.e_commerce_compose.screens.categoryScreen.CategoryWithSubCategories
import com.example.e_commerce_compose.screens.categoryScreen.MenuCategory


@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val expandedCategoryId by viewModel.expandedCategoryId.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.fetchCategories()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Text(
                text = "KATEGORİLER",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(categories) { categoryWithSubs ->
                    MainCategoryItem(
                        categoryWithSubs = categoryWithSubs,
                        isExpanded = categoryWithSubs.category.id == expandedCategoryId,
                        onCategoryClick = { viewModel.onCategoryClick(categoryWithSubs.category.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun MainCategoryItem(
    categoryWithSubs: CategoryWithSubCategories,
    isExpanded: Boolean,
    onCategoryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        // Ana kategori başlığı
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onCategoryClick)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Ana kategori ikonu
                if (categoryWithSubs.category.icon.isNotEmpty() &&
                    !categoryWithSubs.category.icon.startsWith("SubList")) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${"hcapi.sch.awstest.hebiar.com/images"}/${categoryWithSubs.category.icon}")
                            .crossfade(true)
                            .build(),
                        contentDescription = categoryWithSubs.category.name,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit,
                    )
                }
                Text(text = categoryWithSubs.category.name)
            }

            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp
                else Icons.Default.KeyboardArrowDown,
                contentDescription = if (isExpanded) "Daralt" else "Genişlet",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Alt kategoriler
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(start = 32.dp)
            ) {
                // Her bir alt kategori listesi için
                categoryWithSubs.subcategories.forEach { subCategory ->
                    // Alt kategorilerin başlığı (eğer gerekiyorsa)
                    if (subCategory.items.isNotEmpty()) {
                        Text(
                            text = subCategory.list.name,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        // Alt kategori öğeleri
                        subCategory.items.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { /* Alt kategori tıklama işlemi */ }
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "İlerle",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            if (item != subCategory.items.last()) {
                                Divider(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
        Divider()
    }
}