package xyz.stasiak.stufftracker.ui.settings.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun CategorySettingsBody(
    categories: List<Category>,
    onAdd: (String) -> Unit,
    onDelete: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    var categoryName by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        categories.forEach { category ->
            CategoryListItem(category = category.name, onDelete = { onDelete(category) })
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                label = { Text(stringResource(R.string.category)) },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                onAdd(categoryName)
                categoryName = ""
            }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategorySettingsBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        CategorySettingsBody(
            categories = listOf(Category(name = "Category 1"), Category(name = "Category 2")),
            onAdd = {},
            onDelete = {}
        )
    }
}