package xyz.stasiak.stufftracker.ui.item.entry

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
import xyz.stasiak.stufftracker.data.Category
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ItemEntryBody(
    itemDetails: ItemDetails,
    onValueChange: (ItemEntryEvent) -> Unit,
    categories: List<Category>,
    fabHeight: Dp,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = fabHeight + 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            value = itemDetails.name,
            onValueChange = { onValueChange(ItemEntryEvent.NameChanged(it)) },
            label = { Text(stringResource(R.string.name)) },
            isError = !itemDetails.nameValid,
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
                value = itemDetails.category ?: stringResource(R.string.no_item_category),
                onValueChange = { },
                label = { Text(stringResource(R.string.category)) },
                isError = !itemDetails.categoryValid,
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
                    text = { Text(text = stringResource(id = R.string.no_item_category)) },
                    onClick = {
                        onValueChange(ItemEntryEvent.CategoryChanged(null))
                        expanded = false
                    },
                )
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(text = category.name) },
                        onClick = {
                            onValueChange(ItemEntryEvent.CategoryChanged(category.name))
                            expanded = false
                        },
                    )
                }
            }
        }
        OutlinedTextField(
            value = itemDetails.numOfItems,
            onValueChange = { onValueChange(ItemEntryEvent.NumOfItemsChanged(it)) },
            label = { Text(stringResource(R.string.number_of_items)) },
            isError = !itemDetails.numOfItemsValid,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = itemDetails.currentUses,
            onValueChange = { onValueChange(ItemEntryEvent.CurrentUsesChanged(it)) },
            label = { Text(stringResource(R.string.current_uses)) },
            isError = !itemDetails.currentUsesValid,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = itemDetails.usesLeft,
            onValueChange = { onValueChange(ItemEntryEvent.UsesLeftChanged(it)) },
            label = { Text(stringResource(R.string.uses_left)) },
            isError = !itemDetails.usesLeftValid,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = itemDetails.usesPerItem,
            onValueChange = { onValueChange(ItemEntryEvent.UsesPerItemChanged(it)) },
            label = { Text(stringResource(R.string.uses_per_item)) },
            isError = !itemDetails.usesPerItemValid,
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
        ItemEntryBody(
            itemDetails = ItemDetails(
                name = "Name",
                category = "Category",
                numOfItems = "1",
                currentUses = "1",
                usesLeft = "1",
                usesPerItem = "1",
            ),
            onValueChange = {},
            categories = listOf(Category(name = "Category")),
            fabHeight = (-16).dp
        )
    }
}