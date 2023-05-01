package xyz.stasiak.stufftracker.ui.item.details

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.ui.AppViewModelProvider

@Composable
fun ItemDetailsScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ItemDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState().value
    val itemId = if (uiState is ItemDetailsUiState.Content) uiState.item.id else -1
    val itemName = if (uiState is ItemDetailsUiState.Content) uiState.item.name else ""
    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(ItemDetailsDestination.titleRes, itemName),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditItem(itemId) },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_item),
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        ItemDetailsBody(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}