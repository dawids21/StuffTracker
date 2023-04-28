package xyz.stasiak.stufftracker

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import xyz.stasiak.stufftracker.ui.theme.StuffTrackerTheme

@Composable
fun StuffTrackerTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = { }
) {
    if (canNavigateBack) {
        TopAppBar(
            title = { Text(text = title) },
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            },
            actions = actions
        )
    } else {
        TopAppBar(title = { Text(text = title) }, modifier = modifier, actions = actions)
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    StuffTrackerTheme(dynamicColor = false, darkTheme = true) {
        StuffTrackerTopAppBar(title = "Stuff Tracker", canNavigateBack = false, actions = {
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