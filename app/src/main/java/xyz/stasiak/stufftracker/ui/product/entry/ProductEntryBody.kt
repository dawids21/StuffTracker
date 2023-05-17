package xyz.stasiak.stufftracker.ui.product.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ProductEntryBody(
    productDetailsEntry: ProductDetailsEntry,
    onValueChange: (ProductEntryEvent) -> Unit,
    categories: List<Category>,
    fabHeight: Dp,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val category = categories.find { it.id == productDetailsEntry.categoryId }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = fabHeight + 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ImageInput(
            imageUri = productDetailsEntry.image,
            onValueChange = { onValueChange(ProductEntryEvent.ImageChanged(it.toString())) })
        OutlinedTextField(
            value = productDetailsEntry.name,
            onValueChange = { onValueChange(ProductEntryEvent.NameChanged(it)) },
            label = { Text(stringResource(R.string.name)) },
            isError = !productDetailsEntry.nameValid,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            OutlinedTextField(
                readOnly = true,
                value = category?.name
                    ?: stringResource(R.string.no_product_category),
                onValueChange = { },
                label = { Text(stringResource(R.string.category)) },
                isError = !productDetailsEntry.categoryValid,
                singleLine = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .exposedDropdownSize()
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.no_product_category)) },
                    onClick = {
                        onValueChange(ProductEntryEvent.CategoryChanged(null))
                        expanded = false
                    },
                )
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category.name) },
                        onClick = {
                            onValueChange(ProductEntryEvent.CategoryChanged(category.id))
                            expanded = false
                        },
                    )
                }
            }
        }
        OutlinedTextField(
            value = productDetailsEntry.numOfItems,
            onValueChange = { onValueChange(ProductEntryEvent.NumOfItemsChanged(it)) },
            label = { Text(stringResource(R.string.number_of_items)) },
            isError = !productDetailsEntry.numOfItemsValid,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemEditBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ProductEntryBody(
            productDetailsEntry = ProductDetailsEntry(
                name = "Name",
                categoryId = 1,
                numOfItems = "1"
            ),
            onValueChange = {},
            categories = listOf(Category(id = 1, name = "Category", userId = "")),
            fabHeight = (-16).dp
        )
    }
}