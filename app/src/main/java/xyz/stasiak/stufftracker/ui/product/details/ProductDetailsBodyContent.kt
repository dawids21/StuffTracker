package xyz.stasiak.stufftracker.ui.product.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.ui.product.ProductImage

@Composable
fun ProductDetailsBodyContent(
    product: Product,
    onProductUse: (Product) -> Unit,
    onProductDeplete: (Product) -> Unit,
    onProductBuy: (Product) -> Unit,
    onProductAddedToList: () -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        ProductImage(
            product.image,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .aspectRatio(1f)
                .align(CenterHorizontally)
        )
        ProductHeader(
            product,
            onProductUse = onProductUse,
            onProductDeplete = onProductDeplete,
            onProductBuy = onProductBuy,
            onProductAddedToList = onProductAddedToList
        )
        ProductParameters(product = product)
    }
}