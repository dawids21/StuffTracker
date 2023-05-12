package xyz.stasiak.stufftracker.ui.product.details

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.ui.product.ProductImage

@Composable
fun ProductDetailsBodyContent(product: Product) {
    ProductImage(product.image, modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f))
    ProductHeader(product)
    ProductParameters(product = product)
}