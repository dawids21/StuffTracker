package xyz.stasiak.stufftracker.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import xyz.stasiak.stufftracker.data.ItemsRepository
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val filtersShown = rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                actions = {
                    IconButton(onClick = { /* do something */ }) {
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
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                    IconButton(onClick = { filtersShown.value = !filtersShown.value }) {
                        Icon(
                            Icons.Filled.FilterList,
                            contentDescription = stringResource(R.string.filter)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        HomeBody(
            itemList = ItemsRepository.getItems(),
            onItemClick = { },
            filtersShown = filtersShown.value,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    itemList: List<Item>,
    onItemClick: (Int) -> Unit,
    filtersShown: Boolean,
    modifier: Modifier = Modifier
) {
    val filteredCategories = remember { mutableStateListOf<String>() }
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (itemList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_item_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                ItemList(
                    itemList = itemList,
                    onItemClick = { onItemClick(it.id) },
                    filteredCategories = filteredCategories
                )
            }
        }
        if (filtersShown) {
            ItemFilter(
                categories = itemList.map { it.category }.distinct(),
                filtered = filteredCategories,
                addToFilter = { filteredCategories.add(it) },
                removeFromFilter = { filteredCategories.remove(it) }
            )
        }
    }
}

@Composable
private fun ItemList(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    filteredCategories: List<String>,
    modifier: Modifier = Modifier
) {
    val filteredItems =
        itemList.filter { filteredCategories.isEmpty() || filteredCategories.contains(it.category) }
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
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .clickable(onClick = { onItemClick(item) }),
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
        HomeBody(itemList = ItemsRepository.getItems(), onItemClick = {}, filtersShown = false)
    }
}

@Preview(showBackground = true)
@Composable
fun ItemEntryPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemEntry(item = ItemsRepository.getItems().first(), onItemClick = {})
    }
}