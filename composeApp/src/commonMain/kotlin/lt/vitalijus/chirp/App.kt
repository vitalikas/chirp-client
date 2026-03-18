package lt.vitalijus.chirp

import androidx.compose.runtime.Composable
import lt.vitalijus.auth.presentation.register.RegisterRoot
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        RegisterRoot(
            onRegisterSuccess = { email ->
                println("Email: $email")
            }
        )
    }
}
