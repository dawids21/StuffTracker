package xyz.stasiak.stufftracker.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.StuffTrackerBottomAppBar
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.data.Item
import xyz.stasiak.stufftracker.data.MockItemsRepository
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun HomeScreen(
    navigateToItemAdd: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    navigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val showSearch = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                actions = {
                    IconButton(onClick = { navigateToSettings() }) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.settings)
                        )
                    }
                }
            )
        },
        bottomBar = {
            StuffTrackerBottomAppBar(
                onFabClick = navigateToItemAdd,
                actions = {
                    IconButton(onClick = { showSearch.value = !showSearch.value }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        HomeBody(
            itemList = MockItemsRepository.getItems(),
            onItemClick = { navigateToItemUpdate(it) },
            showSearch = showSearch.value,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    itemList: List<Item>,
    onItemClick: (Int) -> Unit,
    showSearch: Boolean,
    modifier: Modifier = Modifier
) {
    var searchValue by remember(showSearch) { mutableStateOf("") }
    val filteredCategories = remember { mutableStateListOf<String>() }
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (showSearch) {
            ItemSearch(
                searchValue = searchValue,
                onSearch = { searchValue = it },
            )
        }
        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            ItemFilter(
                categories = itemList.map { it.category }.distinct(),
                filtered = filteredCategories,
                addToFilter = { filteredCategories.add(it) },
                removeFromFilter = { filteredCategories.remove(it) }
            )
            ItemList(
                itemList = itemList,
                onItemClick = { onItemClick(it.id) },
                searchValue = searchValue,
                filteredCategories = filteredCategories
            )
        }
    }
}

@Composable
private fun ItemList(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    searchValue: String,
    filteredCategories: List<String>,
    modifier: Modifier = Modifier
) {
    val filteredItems =
        itemList.filter { filteredCategories.isEmpty() || filteredCategories.contains(it.category) }
            .filter { searchValue.isBlank() || it.name.contains(searchValue, ignoreCase = true) }
    LazyColumn(modifier = modifier) {
        items(items = filteredItems, key = { it.id }) { item ->
            ItemEntry(item = item, onItemClick = onItemClick)
            Divider()
        }
    }
}

@Composable
private fun ItemEntry(
    item: Item,
    onItemClick: (Item) -> Unit,
    modifier: Modifier = Modifier
) {
    val useAction = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        background = MaterialTheme.colorScheme.primary,
        onSwipe = {}
    )
    val restoreAction = SwipeAction(
        icon = {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        },
        background = MaterialTheme.colorScheme.secondary,
        onSwipe = {}
    )
    SwipeableActionsBox(
        startActions = listOf(useAction),
        endActions = listOf(restoreAction),
        modifier = modifier
            .clickable(onClick = { onItemClick(item) })
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.item_entry_items, item.numOfItems),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = stringResource(id = R.string.item_entry_uses_left, item.usesLeft),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Text(
                text = item.category,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Top)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        HomeBody(
            itemList = MockItemsRepository.getItems(),
            onItemClick = {},
            showSearch = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemEntryPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemEntry(item = MockItemsRepository.getItems().first(), onItemClick = {})
    }
}