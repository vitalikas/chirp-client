package lt.vitalijus.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import lt.vitalijus.auth.presentation.navigation.AuthGraphRoutes
import lt.vitalijus.auth.presentation.navigation.authGraph
import lt.vitalijus.chat.presentation.chat_list.ChatListRoute
import lt.vitalijus.chat.presentation.chat_list.ChatListScreenRoot

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthGraphRoutes.Graph,
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {
                navController.navigate(ChatListRoute) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            }
        )
        composable<ChatListRoute> {
            ChatListScreenRoot()
        }
    }
}
