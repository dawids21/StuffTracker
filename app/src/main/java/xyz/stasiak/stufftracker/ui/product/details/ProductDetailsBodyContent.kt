package xyz.stasiak.stufftracker.ui.product.details

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
    ProductImage(
        product.image,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
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