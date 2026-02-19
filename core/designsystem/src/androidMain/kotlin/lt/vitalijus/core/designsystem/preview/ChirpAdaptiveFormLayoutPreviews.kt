package lt.vitalijus.core.designsystem.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices.NEXUS_10
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import lt.vitalijus.core.designsystem.components.brand.ChirpBrandLogo
import lt.vitalijus.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import lt.vitalijus.core.designsystem.theme.ChirpTheme


@Composable
@PreviewLightDark
@PreviewScreenSizes
@Preview(
    device = NEXUS_10
)
private fun ChirpAdaptiveFormLayoutPreview() {
    ChirpTheme {
        ChirpAdaptiveFormLayout(
            headerText = "Welcome to Chirp!",
            errorText = "Login failed, please try again.",
            logo = { ChirpBrandLogo() },
            formContent = {
                Text(
                    text = "Sample form content 1",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Sample form content 2",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        )
    }
}
