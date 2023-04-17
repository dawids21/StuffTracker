package xyz.stasiak.stufftracker.ui.login

import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.navigation.NavigationDestination

object LoginDestination : NavigationDestination {
    override val route: String = "login"
    override val titleRes: Int = R.string.app_name
}