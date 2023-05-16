package xyz.stasiak.stufftracker.ui.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.StuffTrackerBottomAppBar
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.ui.AppViewModelProvider
import xyz.stasiak.stufftracker.ui.DepleteDialog
import xyz.stasiak.stufftracker.ui.DialogState
import xyz.stasiak.stufftracker.ui.RemindDialog
import xyz.stasiak.stufftracker.ui.product.ProductImage
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme
import kotlin.math.floor

@Composable
fun HomeScreen(
    navigateToProductAdd: () -> Unit,
    navigateToProductUpdate: (Int) -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val showSearch = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val toastShowState = viewModel.toastShowState
    LaunchedEffect(toastShowState) {
        if (toastShowState is ToastShowState.Show) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.no_items_left, toastShowState.productName),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.onToastShown()
        }
    }

    val remindDialogState = viewModel.remindDialogState
    if (remindDialogState is DialogState.Showing) {
        RemindDialog(
            productName = remindDialogState.product.name,
            onDialogDismissed = { viewModel.onRemindDialogDismissed() }
        )
    }

    val depleteDialogState = viewModel.depleteDialogState
    if (depleteDialogState is DialogState.Showing) {
        DepleteDialog(
            productName = depleteDialogState.product.name,
            onDepletionConfirmed = { viewModel.depleteItem(depleteDialogState.product) },
            onDialogDismissed = { viewModel.onDepleteDialogDismissed() }
        )
    }

    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                actions = {
                    IconButton(onClick = { navigateToSettings() }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    }
                }
            )
        },
        bottomBar = {
            StuffTrackerBottomAppBar(
                onFabClick = navigateToProductAdd,
                actions = {
                    IconButton(onClick = { showSearch.value = !showSearch.value }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        HomeBody(
            productList = products,
            categories = categories,
            onProductClick = { navigateToProductUpdate(it) },
            onProductUse = { viewModel.useItem(it.productId) },
            onProductDeplete = { viewModel.depleteItem(it) },
            onProductBuy = { viewModel.buyProduct(it) },
            showSearch = showSearch.value,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    productList: List<Product>,
    categories: List<Category>,
    onProductClick: (Int) -> Unit,
    onProductUse: (Product) -> Unit,
    onProductDeplete: (Product) -> Unit,
    onProductBuy: (Product) -> Unit,
    showSearch: Boolean,
    modifier: Modifier = Modifier
) {
    var searchValue by remember(showSearch) { mutableStateOf("") }
    val filteredCategories = remember { mutableStateListOf<Category>() }
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (showSearch) {
            ProductSearch(
                searchValue = searchValue,
                onSearch = { searchValue = it },
            )
        }
        if (productList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_product_description),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            ProductFilter(
                categories = categories,
                filtered = filteredCategories,
                addToFilter = { filteredCategories.add(it) },
                removeFromFilter = { filteredCategories.remove(it) }
            )
            ProductList(
                productList = productList,
                onProductClick = { onProductClick(it.id) },
                onProductUse = onProductUse,
                onProductDeplete = onProductDeplete,
                onProductBuy = onProductBuy,
                searchValue = searchValue,
                filteredCategories = filteredCategories
            )
        }
    }
}

@Composable
private fun ProductList(
    productList: List<Product>,
    onProductClick: (Product) -> Unit,
    onProductUse: (Product) -> Unit,
    onProductDeplete: (Product) -> Unit,
    onProductBuy: (Product) -> Unit,
    searchValue: String,
    filteredCategories: List<Category>,
    modifier: Modifier = Modifier
) {
    val filteredProducts =
        productList.filter {
            filteredCategories.isEmpty() || filteredCategories.map { category -> category.name }
                .contains(it.category)
        }
            .filter { searchValue.isBlank() || it.name.contains(searchValue, ignoreCase = true) }
    LazyColumn(modifier = modifier) {
        items(items = filteredProducts, key = { it.id }) { product ->
            ProductEntry(
                product = product,
                onProductClick = onProductClick,
                onProductUse = onProductUse,
                onProductDeplete = onProductDeplete,
                onProductBuy = onProductBuy
            )
            Divider()
        }
    }
}

@Composable
private fun ProductEntry(
    product: Product,
    onProductClick: (Product) -> Unit,
    onProductUse: (Product) -> Unit,
    onProductDeplete: (Product) -> Unit,
    onProductBuy: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val useAction = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        background = MaterialTheme.colorScheme.primary,
        onSwipe = { onProductUse(product) }
    )
    val depleteAction = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Filled.Cancel,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        },
        background = MaterialTheme.colorScheme.secondary,
        onSwipe = { onProductDeplete(product) }
    )
    val buyAction = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        background = MaterialTheme.colorScheme.primary,
        onSwipe = { onProductBuy(product) }
    )
    SwipeableActionsBox(
        startActions = listOf(useAction, depleteAction),
        endActions = listOf(buyAction),
        modifier = modifier
            .clickable(onClick = { onProductClick(product) })
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProductImage(
                imageUri = product.image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.product_entry_items, product.numOfItems),
                    style = MaterialTheme.typography.labelMedium
                )
                if (product.isCalculated) {
                    val usesLeft = floor(product.averageUses - product.lastItemUses).toInt()
                    Text(
                        text = stringResource(
                            id = R.string.product_entry_uses_left,
                            if (usesLeft > 0) usesLeft else 0
                        ),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Text(
                text = product.category ?: stringResource(id = R.string.no_product_category),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Top)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        HomeBody(
            productList = listOf(
                Product(
                    productId = 1,
                    name = "Coffee",
                    numOfItems = 1,
                    category = "Coffee",
                    image = null,
                    averageUses = 10f,
                    lastItemUses = 5,
                    isCalculated = true,
                    remindDialogShown = false,
                    depletedDialogShown = false
                ),
                Product(
                    productId = 2,
                    name = "Shampoo",
                    numOfItems = 1,
                    category = "Hygiene",
                    image = null,
                    averageUses = 10f,
                    lastItemUses = 5,
                    isCalculated = true,
                    remindDialogShown = false,
                    depletedDialogShown = false
                ),
            ),
            categories = listOf(
                Category(name = "Coffee"),
                Category(name = "Hygiene"),
                Category(name = "Food"),
                Category(name = "Sport"),
            ),
            onProductClick = {},
            onProductUse = {},
            onProductDeplete = {},
            onProductBuy = {},
            showSearch = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductEntryPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ProductEntry(
            product = Product(
                productId = 2,
                name = "Shampoo",
                numOfItems = 1,
                category = "Hygiene",
                image = null,
                averageUses = 10f,
                lastItemUses = 5,
                isCalculated = true,
                remindDialogShown = false,
                depletedDialogShown = false
            ),
            onProductClick = {},
            onProductUse = {},
            onProductDeplete = {},
            onProductBuy = {}
        )
    }
}