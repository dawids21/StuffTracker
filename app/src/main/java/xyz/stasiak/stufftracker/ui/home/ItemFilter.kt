package xyz.stasiak.stufftracker.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun ItemFilter(
    categories: List<String>,
    filtered: List<String>,
    addToFilter: (String) -> Unit,
    removeFromFilter: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shadowElevation = 8.dp, modifier = modifier
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            for (category in categories) {
                val isFiltered = category in filtered
                val onClick = if (isFiltered) {
                    { removeFromFilter(category) }
                } else {
                    { addToFilter(category) }
                }
                FilterChip(
                    label = { Text(category) },
                    selected = isFiltered,
                    onClick = onClick,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ItemFilterPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        ItemFilter(
            categories = listOf("Coffee", "Hygiene", "Food", "Sport"),
            filtered = listOf("Coffee", "Sport"),
            addToFilter = {},
            removeFromFilter = {}
        )
    }
}