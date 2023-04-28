package xyz.stasiak.stufftracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import xyz.stasiak.stufftracker.ui.navigation.StuffTrackerNavHost

@Composable
fun StuffTrackerApp(navController: NavHostController = rememberNavController()) {
    StuffTrackerNavHost(navController = navController)
}