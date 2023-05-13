package xyz.stasiak.stufftracker.ui.product.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.ui.LoadingIndicator
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ProductDetailsBody(
    uiState: ProductDetailsUiState,
    onProductUse: (Product) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (uiState) {
            is ProductDetailsUiState.NavigateBack -> LaunchedEffect(Unit) {
                navigateBack()
            }

            is ProductDetailsUiState.Loading -> LoadingIndicator()
            is ProductDetailsUiState.Content -> ProductDetailsBodyContent(
                uiState.product,
                onProductUse = onProductUse
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsBodyLoadingPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ProductDetailsBody(ProductDetailsUiState.Loading, {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailsBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ProductDetailsBody(
            ProductDetailsUiState.Content(
                Product(
                    productId = 2,
                    name = "Shampoo",
                    numOfItems = 1,
                    category = "Hygiene",
                    image = null,
                    averageUses = 10f,
                    lastItemUses = 5,
                    isCalculated = true
                ),
            ),
            {},
            {}
        )
    }
}