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
import xyz.stasiak.stufftracker.ui.login.LoginDestination
import xyz.stasiak.stufftracker.ui.login.LoginScreen
import xyz.stasiak.stufftracker.ui.product.details.ProductDetailsDestination
import xyz.stasiak.stufftracker.ui.product.details.ProductDetailsScreen
import xyz.stasiak.stufftracker.ui.product.entry.add.ProductAddDestination
import xyz.stasiak.stufftracker.ui.product.entry.add.ProductAddScreen
import xyz.stasiak.stufftracker.ui.product.entry.edit.ProductEditDestination
import xyz.stasiak.stufftracker.ui.product.entry.edit.ProductEditScreen
import xyz.stasiak.stufftracker.ui.settings.SettingsDestination
import xyz.stasiak.stufftracker.ui.settings.SettingsScreen
import xyz.stasiak.stufftracker.ui.settings.category.CategorySettingsDestination
import xyz.stasiak.stufftracker.ui.settings.category.CategorySettingsScreen
import xyz.stasiak.stufftracker.ui.settings.shoppinglist.ShoppingListSettingsDestination
import xyz.stasiak.stufftracker.ui.settings.shoppinglist.ShoppingListSettingsScreen

@Composable
fun StuffTrackerNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToProductAdd = {
                    navController.navigate(ProductAddDestination.route)
                },
                navigateToProductUpdate = {
                    navController.navigate("${ProductDetailsDestination.route}/${it}")
                },
                navigateToSettings = { navController.navigate(SettingsDestination.route) }
            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productIdArg) {
                type = NavType.IntType
            })
        ) {
            ProductDetailsScreen(
                navigateToEditProduct = {
                    navController.navigate("${ProductEditDestination.route}/$it")
                },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = ProductEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductEditDestination.productIdArg) {
                type = NavType.IntType
            })
        ) {
            ProductEditScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = SettingsDestination.route) {
            SettingsScreen(
                onCategoriesClick = { navController.navigate(CategorySettingsDestination.route) },
                onGoogleKeepClick = { navController.navigate(ShoppingListSettingsDestination.route) },
                navigateBack = { navController.navigateUp() },
                navigateToLogin = {
                    navController.navigate(LoginDestination.route) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                }

            )
        }
        composable(route = CategorySettingsDestination.route) {
            CategorySettingsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = ShoppingListSettingsDestination.route) {
            ShoppingListSettingsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = ProductAddDestination.route) {
            ProductAddScreen(
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
