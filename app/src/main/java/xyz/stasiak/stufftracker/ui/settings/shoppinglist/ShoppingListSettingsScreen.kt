package xyz.stasiak.stufftracker.ui.settings.shoppinglist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar

@Composable
fun ShoppingListSettingsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(ShoppingListSettingsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ShoppingListSettingsBody(
            onSaveClick = navigateBack,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
