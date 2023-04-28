package xyz.stasiak.stufftracker.ui.item.edit

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.data.ItemsRepository

@Composable
fun ItemEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val item = ItemsRepository.getItems().first()
    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(ItemEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateBack() },
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.save_item),
                )
            }
        },
    ) { innerPadding ->
        Text(item.name, modifier = modifier.padding(innerPadding))
    }
}