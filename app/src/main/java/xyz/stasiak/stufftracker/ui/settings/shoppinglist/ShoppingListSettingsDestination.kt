package xyz.stasiak.stufftracker.ui.settings.shoppinglist

import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.navigation.NavigationDestination

object ShoppingListSettingsDestination : NavigationDestination {
    override val route: String = "shopping_list_settings"
    override val titleRes: Int = R.string.google_keep
}
