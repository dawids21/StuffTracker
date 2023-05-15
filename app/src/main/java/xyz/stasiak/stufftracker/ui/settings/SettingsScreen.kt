package xyz.stasiak.stufftracker.ui.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.ui.AppViewModelProvider

@Composable
fun SettingsScreen(
    onCategoriesClick: () -> Unit,
    onGoogleKeepClick: () -> Unit,
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val loggedOut = viewModel.loggedOut
    LaunchedEffect(loggedOut) {
        if (loggedOut) {
            navigateToLogin()
        }
    }

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
            onSignOutClick = { viewModel.onLogoutClick() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
