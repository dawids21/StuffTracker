package xyz.stasiak.stufftracker.ui.product.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.data.product.Product
import kotlin.math.floor

@Composable
fun ProductParameters(product: Product, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(id = R.string.product_num_of_items, product.numOfItems),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(id = R.string.product_current_uses, product.lastItemUses),
            style = MaterialTheme.typography.bodyMedium
        )
        if (product.isCalculated) {
            val usesLeft = floor(product.averageUses - product.lastItemUses).toInt()
            Text(
                text = stringResource(
                    id = R.string.product_uses_left, if (usesLeft > 0) usesLeft else 0
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Text(
            text = stringResource(id = R.string.product_uses_per_item, product.averageUses),
            style = MaterialTheme.typography.bodyMedium
        )

    }
}