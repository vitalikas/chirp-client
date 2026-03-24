package lt.vitalijus.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import lt.vitalijus.auth.presentation.navigation.AuthGraphRoutes
import lt.vitalijus.auth.presentation.navigation.authGraph

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthGraphRoutes.Graph,
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {

            }
        )
    }
}
