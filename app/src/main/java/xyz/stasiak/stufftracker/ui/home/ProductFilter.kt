package xyz.stasiak.stufftracker.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.data.category.Category
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ProductFilter(
    categories: List<Category>,
    filtered: List<Category>,
    addToFilter: (Category) -> Unit,
    removeFromFilter: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center
    ) {
        for (category in categories) {
            val isFiltered = category in filtered
            val onClick = if (isFiltered) {
                { removeFromFilter(category) }
            } else {
                { addToFilter(category) }
            }
            FilterChip(
                label = { Text(category.name) },
                selected = isFiltered,
                onClick = onClick,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ItemFilterPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ProductFilter(
            categories = listOf(
                Category(name = "Coffee"),
                Category(name = "Hygiene"),
                Category(name = "Food"),
                Category(name = "Sport"),
            ),
            filtered = listOf(
                Category(name = "Hygiene"),
                Category(name = "Food"),
            ),
            addToFilter = {},
            removeFromFilter = {}
        )
    }
}