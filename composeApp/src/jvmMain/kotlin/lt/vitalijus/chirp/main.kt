package lt.vitalijus.chirp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import lt.vitalijus.chirp.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Chirp",
        ) {
            App()
        }
    }
}