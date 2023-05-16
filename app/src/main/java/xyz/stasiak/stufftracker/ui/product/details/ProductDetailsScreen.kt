package xyz.stasiak.stufftracker.ui.product.details

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.ui.AppViewModelProvider
import xyz.stasiak.stufftracker.ui.DepleteDialog
import xyz.stasiak.stufftracker.ui.DialogState
import xyz.stasiak.stufftracker.ui.RemindDialog
import xyz.stasiak.stufftracker.ui.home.ToastShowState

@Composable
fun ProductDetailsScreen(
    navigateToEditProduct: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    val productId = if (uiState is ProductDetailsUiState.Content) uiState.product.id else -1
    val productName = if (uiState is ProductDetailsUiState.Content) uiState.product.name else ""
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val toastShowState = viewModel.toastShowState
    LaunchedEffect(toastShowState) {
        if (toastShowState is ToastShowState.Show) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.no_items_left, toastShowState.productName),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.onToastShown()
        }
    }

    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, productName)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)

    val remindDialogState = viewModel.remindDialogState
    if (remindDialogState is DialogState.Showing) {
        RemindDialog(
            productName = remindDialogState.product.name,
            productNumOfItems = remindDialogState.product.numOfItems,
            onAddToShoppingList = { context.startActivity(shareIntent) },
            onDialogDismissed = { viewModel.onRemindDialogDismissed() }
        )
    }

    val depleteDialogState = viewModel.depleteDialogState
    if (depleteDialogState is DialogState.Showing) {
        DepleteDialog(
            productName = depleteDialogState.product.name,
            onDepletionConfirmed = { viewModel.depleteItem(depleteDialogState.product) },
            onDialogDismissed = { viewModel.onDepleteDialogDismissed() }
        )
    }

    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(ProductDetailsDestination.titleRes, productName),
                canNavigateBack = true,
                navigateUp = navigateBack,
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(
                                R.string.product_menu
                            )
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.reset)) },
                            onClick = { viewModel.onResetClicked() }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.delete_product)) },
                            onClick = { viewModel.onDeleteClicked() }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditProduct(productId) },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_product),
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        ProductDetailsBody(
            uiState = uiState,
            onProductUse = { viewModel.useItem(it.productId) },
            onProductDeplete = { viewModel.depleteItem(it) },
            onProductBuy = { viewModel.buyProduct(it) },
            onProductAddedToList = { context.startActivity(shareIntent) },
            navigateBack = navigateBack,
            modifier = Modifier.padding(innerPadding)
        )
    }
}