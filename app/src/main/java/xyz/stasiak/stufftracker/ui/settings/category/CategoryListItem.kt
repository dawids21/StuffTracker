package xyz.stasiak.stufftracker.ui.settings.category

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.data.category.Category

@Composable
fun CategoryListItem(
    category: Category,
    onEdit: (Category, String) -> Unit,
    onDelete: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var categoryName by remember(category) { mutableStateOf(category.name) }
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        if (isEditing) {
            OutlinedTextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    onEdit(category, categoryName)
                    isEditing = false
                },
            ) {
                Icon(Icons.Filled.Save, contentDescription = stringResource(R.string.edit_category))
            }
        } else {
            Text(
                text = category.name,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { isEditing = true },
            ) {
                Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit_category))
            }
        }
        IconButton(
            onClick = { onDelete(category) },
        ) {
            Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.delete_category))
        }
    }
}