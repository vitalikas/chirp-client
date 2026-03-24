package lt.vitalijus.core.designsystem.components.brand

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.success_checkmark
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import lt.vitalijus.core.designsystem.theme.extended
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpFailureIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.error,
        modifier = modifier
    )
}

@Composable
@Preview
fun ChirpFailureIconPreview(darkTheme: Boolean) {
    ChirpTheme(
        darkTheme = darkTheme
    ) {
        ChirpFailureIcon()
    }
}

@Composable
@Preview
fun ChirpFailureIconPreviewLight() {
    ChirpSuccessIconPreview(darkTheme = false)
}

@Composable
@Preview
fun ChirpFailureIconPreviewDark() {
    ChirpSuccessIconPreview(darkTheme = true)
}
