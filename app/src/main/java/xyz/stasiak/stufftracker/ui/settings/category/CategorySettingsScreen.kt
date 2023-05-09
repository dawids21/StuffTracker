package xyz.stasiak.stufftracker.ui.settings.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.ui.AppViewModelProvider

@Composable
fun CategorySettingsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategorySettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val categories by viewModel.categories.collectAsState()
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
            onAdd = {viewModel.addCategory(it)},
            onDelete = {viewModel.deleteCategory(it)},
            modifier = Modifier.padding(innerPadding)
        )
    }
}
