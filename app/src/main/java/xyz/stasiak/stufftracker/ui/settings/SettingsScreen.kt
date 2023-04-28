package xyz.stasiak.stufftracker.ui.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar

@Composable
fun SettingsScreen(
    onCategoriesClick: () -> Unit,
    onGoogleKeepClick: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(SettingsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        SettingsBody(
            onCategoriesClick = onCategoriesClick,
            onGoogleKeepClick = onGoogleKeepClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
