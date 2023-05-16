package xyz.stasiak.stufftracker.ui.product.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.data.product.Product
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ProductHeader(
    product: Product,
    onProductUse: (Product) -> Unit,
    onProductDeplete: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.category ?: stringResource(R.string.no_product_category),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row {
            IconButton(onClick = { onProductUse(product) }) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = stringResource(R.string.use_item)
                )
            }
            IconButton(onClick = { onProductDeplete(product) }) {
                Icon(
                    Icons.Outlined.Cancel,
                    contentDescription = stringResource(R.string.deplete_product)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Outlined.Refresh,
                    contentDescription = stringResource(R.string.buy_product)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Outlined.ShoppingBag,
                    contentDescription = stringResource(R.string.add_product_to_shopping_list)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductHeaderPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ProductHeader(
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
            onProductUse = {},
            onProductDeplete = {},
        )

    }
}