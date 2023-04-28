package xyz.stasiak.stufftracker.ui.item.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.data.Item
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ItemEditBody(item: Item, fabHeight: Dp, modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf(item.name) }
    var nameValid by remember { mutableStateOf(true) }
    fun validateName() {
        nameValid = name.isNotBlank()
    }

    var category by remember { mutableStateOf(item.category) }
    var categoryValid by remember { mutableStateOf(true) }
    fun validateCategory() {
        categoryValid = category.isNotBlank()
    }

    var numOfItems by remember { mutableStateOf(item.numOfItems.toString()) }
    var numOfItemsValid by remember { mutableStateOf(true) }
    fun validateNumOfItems() {
        numOfItemsValid = numOfItems.isNotBlank() && numOfItems.toIntOrNull() != null
    }

    var currentUses by remember { mutableStateOf(item.currentUses.toString()) }
    var currentUsesValid by remember { mutableStateOf(true) }
    fun validateCurrentUses() {
        currentUsesValid = currentUses.isNotBlank() && currentUses.toIntOrNull() != null
    }

    var usesPerItem by remember { mutableStateOf(item.usesPerItem.toString()) }
    var usesPerItemValid by remember { mutableStateOf(true) }
    fun validateUsesPerItem() {
        usesPerItemValid = usesPerItem.isNotBlank() && usesPerItem.toIntOrNull() != null
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .padding(bottom = fabHeight + 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            painter = painterResource(item.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                validateName()
            },
            label = { Text(stringResource(R.string.name)) },
            isError = !nameValid,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = category,
            onValueChange = {
                category = it
                validateCategory()
            },
            label = { Text(stringResource(R.string.category)) },
            isError = !categoryValid,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = numOfItems,
            onValueChange = {
                numOfItems = it
                validateNumOfItems()
            },
            label = { Text(stringResource(R.string.number_of_items)) },
            isError = !numOfItemsValid,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = currentUses,
            onValueChange = {
                currentUses = it
                validateCurrentUses()
            },
            label = { Text(stringResource(R.string.current_uses)) },
            isError = !currentUsesValid,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = usesPerItem,
            onValueChange = {
                usesPerItem = it
                validateUsesPerItem()
            },
            label = { Text(stringResource(R.string.uses_per_item)) },
            isError = !usesPerItemValid,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemEditBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemEditBody(
            Item(1, "name", "description", 1, 1, 1, 1, R.drawable.shampoo),
            (-16).dp
        )
    }
}