package xyz.stasiak.stufftracker.ui.settings.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.data.MockItemsRepository

@Composable
fun CategorySettingsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = MockItemsRepository.getItems().map { it.category }.distinct().sorted()
    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(CategorySettingsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        CategorySettingsBody(
            categories = categories,
            onAdd = {},
            onDelete = {},
            modifier = Modifier.padding(innerPadding)
        )
    }
}
