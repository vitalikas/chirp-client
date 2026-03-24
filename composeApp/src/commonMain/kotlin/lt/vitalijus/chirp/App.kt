package lt.vitalijus.chirp

import androidx.compose.runtime.Composable
import lt.vitalijus.chirp.navigation.NavigationRoot
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        NavigationRoot()
    }
}
