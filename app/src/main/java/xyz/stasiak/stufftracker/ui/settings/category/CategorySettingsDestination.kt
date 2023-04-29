package xyz.stasiak.stufftracker.ui.settings.category

import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.navigation.NavigationDestination

object CategorySettingsDestination : NavigationDestination {
    override val route: String = "category_settings"
    override val titleRes: Int = R.string.categories
}
