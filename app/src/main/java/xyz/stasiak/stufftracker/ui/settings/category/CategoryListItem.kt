package xyz.stasiak.stufftracker.ui.settings.category

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.R

@Composable
fun CategoryListItem(category: String, onDelete: (String) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = category,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = { onDelete(category) },
        ) {
            Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.delete_category))
        }
    }
}