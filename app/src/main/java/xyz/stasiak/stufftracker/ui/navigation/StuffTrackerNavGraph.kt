package xyz.stasiak.stufftracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import xyz.stasiak.stufftracker.ui.home.HomeDestination
import xyz.stasiak.stufftracker.ui.home.HomeScreen
import xyz.stasiak.stufftracker.ui.item.details.ItemDetailsDestination
import xyz.stasiak.stufftracker.ui.item.details.ItemDetailsScreen
import xyz.stasiak.stufftracker.ui.item.edit.ItemEditDestination
import xyz.stasiak.stufftracker.ui.item.edit.ItemEditScreen
import xyz.stasiak.stufftracker.ui.login.LoginDestination
import xyz.stasiak.stufftracker.ui.login.LoginScreen
import xyz.stasiak.stufftracker.ui.settings.SettingsDestination
import xyz.stasiak.stufftracker.ui.settings.SettingsScreen

@Composable
fun StuffTrackerNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToHome = { navController.navigate(HomeDestination.route) }
            )
        }
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemUpdate = {
                    navController.navigate("${ItemDetailsDestination.route}/${it}")
                },
                navigateToSettings = { navController.navigate(SettingsDestination.route) }
            )
        }
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemDetailsScreen(
                navigateToEditItem = {
                    navController.navigate("${ItemEditDestination.route}/$it")
                },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen(
                onCategoriesClick = {},
                onGoogleKeepClick = {},
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
