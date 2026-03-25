package lt.vitalijus.chirp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import lt.vitalijus.chirp.navigation.DeepLinkListener
import lt.vitalijus.chirp.navigation.NavigationRoot
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    ChirpTheme {
        DeepLinkListener(navController = navController)
        NavigationRoot(navController = navController)
    }
}
