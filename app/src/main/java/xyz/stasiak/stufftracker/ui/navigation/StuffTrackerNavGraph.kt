package xyz.stasiak.stufftracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.stasiak.stufftracker.ui.home.HomeDestination
import xyz.stasiak.stufftracker.ui.home.HomeScreen
import xyz.stasiak.stufftracker.ui.login.LoginDestination
import xyz.stasiak.stufftracker.ui.login.LoginScreen

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
            HomeScreen()
        }
    }
}
