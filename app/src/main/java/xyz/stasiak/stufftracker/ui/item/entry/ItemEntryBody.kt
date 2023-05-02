package xyz.stasiak.stufftracker.ui.item.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ItemEntryBody(
    itemDetails: ItemDetails,
    onValueChange: (ItemEntryEvent) -> Unit,
    fabHeight: Dp,
    modifier: Modifier = Modifier
) {
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
        OutlinedTextField(
            value = itemDetails.category,
            onValueChange = { onValueChange(ItemEntryEvent.CategoryChanged(it)) },
            label = { Text(stringResource(R.string.category)) },
            isError = !itemDetails.categoryValid,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
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
            fabHeight = (-16).dp
        )
    }
}