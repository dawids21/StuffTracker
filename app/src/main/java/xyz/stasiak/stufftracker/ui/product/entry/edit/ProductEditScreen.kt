package xyz.stasiak.stufftracker.ui.product.entry.edit

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.ui.AppViewModelProvider
import xyz.stasiak.stufftracker.ui.product.entry.ItemEntryBody
import xyz.stasiak.stufftracker.ui.product.entry.ProductEntryEvent
import xyz.stasiak.stufftracker.ui.product.entry.ProductUiStatus

@Composable
fun ProductEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.productEntryUiState
    val categories = viewModel.categories
    var fabHeight by remember { mutableStateOf(0) }
    val heightInDp = with(LocalDensity.current) { fabHeight.toDp() }

    LaunchedEffect(uiState.status) {
        if (uiState.status == ProductUiStatus.SAVED) {
            navigateBack()
        }
    }

    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(ProductEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.handleEvent(ProductEntryEvent.SaveClicked)
                },
                modifier = Modifier
                    .navigationBarsPadding()
                    .onGloballyPositioned { coordinates ->
                        fabHeight = coordinates.size.height
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.save_product),
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        ItemEntryBody(
            productDetailsEntry = viewModel.productEntryUiState.productDetailsEntry,
            onValueChange = viewModel::handleEvent,
            categories = categories,
            fabHeight = heightInDp,
            modifier = Modifier.padding(innerPadding)
        )
    }
}