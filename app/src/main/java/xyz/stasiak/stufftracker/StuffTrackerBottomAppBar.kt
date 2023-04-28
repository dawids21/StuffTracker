package xyz.stasiak.stufftracker

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun StuffTrackerBottomAppBar(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = { }
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        actions = actions,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, stringResource(R.string.add_new_item))
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun BottomAppBarPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        StuffTrackerBottomAppBar(actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.FilterList,
                    contentDescription = stringResource(R.string.filter)
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            }
        }
        )
    }
}