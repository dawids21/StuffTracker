package xyz.stasiak.stufftracker.ui.product.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import xyz.stasiak.stufftracker.data.product.Product

@Composable
fun ProductDetailsBodyContent(product: Product) {
    Image(
        painter = painterResource(product.image),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
    ProductHeader(product)
    ProductParameters(product = product)
}