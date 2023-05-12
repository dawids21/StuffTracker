package xyz.stasiak.stufftracker.ui.product.details

import xyz.stasiak.stufftracker.R
import xyz.stasiak.stufftracker.ui.navigation.NavigationDestination

object ProductDetailsDestination : NavigationDestination {
    override val route = "product_details"
    override val titleRes = R.string.product_detail_title
    const val productIdArg = "productId"
    val routeWithArgs = "$route/{$productIdArg}"
}
