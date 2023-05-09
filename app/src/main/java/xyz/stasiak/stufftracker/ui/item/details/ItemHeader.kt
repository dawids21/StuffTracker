package xyz.stasiak.stufftracker.ui.item.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DeleteForever
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
import xyz.stasiak.stufftracker.data.Item
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ItemHeader(item: Item, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.category ?: stringResource(R.string.no_item_category),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row {
            IconButton(onClick = { }) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = stringResource(R.string.use_item)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Outlined.Refresh,
                    contentDescription = stringResource(R.string.buy_item)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Outlined.ShoppingBag,
                    contentDescription = stringResource(R.string.add_item_to_shopping_list)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Outlined.DeleteForever,
                    contentDescription = stringResource(R.string.delete_item)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemHeaderPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemHeader(
            item = Item(1, "Name", "Category", 1, 1, 1, 1, R.drawable.shampoo)
        )

    }
}