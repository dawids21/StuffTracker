package xyz.stasiak.stufftracker.ui.item.add

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.StuffTrackerTopAppBar
import xyz.stasiak.stufftracker.data.Item
import xyz.stasiak.stufftracker.ui.item.edit.ItemEditBody

@Composable
fun ItemAddScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var fabHeight by remember { mutableStateOf(0) }
    val heightInDp = with(LocalDensity.current) { fabHeight.toDp() }

    Scaffold(
        topBar = {
            StuffTrackerTopAppBar(
                title = stringResource(ItemAddDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateBack()
                },
                modifier = Modifier
                    .navigationBarsPadding()
                    .onGloballyPositioned { coordinates ->
                        fabHeight = coordinates.size.height
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.save_item),
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        ItemEditBody(
            item = Item.Empty,
            fabHeight = heightInDp,
            modifier = Modifier.padding(innerPadding)
        )
    }
}