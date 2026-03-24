package lt.vitalijus.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import lt.vitalijus.auth.presentation.register.RegisterRoot
import lt.vitalijus.auth.presentation.register_success.RegisterSuccessScreenRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Register
    ) {
        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = { email ->
                    navController.navigate(
                        route = AuthGraphRoutes.RegisterSuccess(email = email)
                    )
                }
            )
        }

        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessScreenRoot()
        }
    }
}
