package xyz.stasiak.stufftracker.ui.product.entry.edit

import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.navigation.NavigationDestination

object ProductEditDestination : NavigationDestination {
    override val route = "product_edit"
    override val titleRes = R.string.product_edit_title
    const val productIdArg = "productId"
    val routeWithArgs = "$route/{$productIdArg}"
}
