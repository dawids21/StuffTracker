package xyz.stasiak.stufftracker.ui.item.entry.edit

import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.navigation.NavigationDestination

object ItemEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.item_edit_title
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
}
