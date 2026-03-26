package lt.vitalijus.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import lt.vitalijus.auth.presentation.email_verification.EmailVerificationRoot
import lt.vitalijus.auth.presentation.login.LoginRoot
import lt.vitalijus.auth.presentation.register.RegisterRoot
import lt.vitalijus.auth.presentation.register_success.RegisterSuccessScreenRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Login
    ) {
        composable<AuthGraphRoutes.Login> {
            LoginRoot(
                onLoginSuccess = onLoginSuccess,
                onForgotPasswordClick = {
                    navController.navigate(route = AuthGraphRoutes.ForgotPassword)
                },
                onCreateAccountClick = {
                    navController.navigate(route = AuthGraphRoutes.Register) {
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = { email ->
                    navController.navigate(route = AuthGraphRoutes.RegisterSuccess(email = email))
                },
                onLoginClick = {
                    navController.navigate(route = AuthGraphRoutes.Login) {
                        popUpTo(route = AuthGraphRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AuthGraphRoutes.RegisterSuccess> {
            RegisterSuccessScreenRoot()
        }

        composable<AuthGraphRoutes.EmailVerification>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "http://65.109.238.114/api/auth/verify?token={token}"
                },
                navDeepLink {
                    uriPattern = "chirp://65.109.238.114/api/auth/verify?token={token}"
                }
            )
        ) {
            EmailVerificationRoot()
        }
    }
}
