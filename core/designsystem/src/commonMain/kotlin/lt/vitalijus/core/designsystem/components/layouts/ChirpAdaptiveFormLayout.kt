package lt.vitalijus.core.designsystem.components.layouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import lt.vitalijus.core.designsystem.components.brand.ChirpBrandLogo
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import lt.vitalijus.core.designsystem.theme.extended
import lt.vitalijus.core.presentation.util.DeviceConfiguration
import lt.vitalijus.core.presentation.util.currentDeviceConfiguration
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpAdaptiveFormLayout(
    headerText: String,
    errorText: String? = null,
    logo: @Composable () -> Unit = {},
    formContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = currentDeviceConfiguration()
    val headerColor = if (configuration == DeviceConfiguration.MOBILE_LANDSCAPE) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.extended.textPrimary
    }

    when (configuration) {
        DeviceConfiguration.MOBILE_PORTRAIT ->
            ChirpMobilePortraitLayout(
                logo = logo,
                headerText = headerText,
                headerColor = headerColor,
                errorText = errorText,
                formContent = formContent,
                modifier = modifier
            )

        DeviceConfiguration.MOBILE_LANDSCAPE ->
            ChirpMobileLandscapeLayout(
                logo = logo,
                headerText = headerText,
                headerColor = headerColor,
                errorText = errorText,
                formContent = formContent,
                modifier = modifier
            )

        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP ->
            ChirpTabletDesktopLayout(
                logo = logo,
                headerText = headerText,
                headerColor = headerColor,
                errorText = errorText,
                formContent = formContent,
                modifier = modifier
            )
    }
}

@Composable
private fun ChirpTabletDesktopLayout(
    logo: @Composable (() -> Unit),
    headerText: String,
    headerColor: Color,
    errorText: String?,
    formContent: @Composable (ColumnScope.() -> Unit),
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        logo()
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 32.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthHeaderSection(
                headerText = headerText,
                headerColor = headerColor,
                errorText = errorText
            )
            formContent()
        }
    }
}

@Composable
private fun ChirpMobileLandscapeLayout(
    logo: @Composable (() -> Unit),
    headerText: String,
    headerColor: Color,
    errorText: String?,
    formContent: @Composable (ColumnScope.() -> Unit),
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .consumeWindowInsets(WindowInsets.displayCutout)
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            logo()
            AuthHeaderSection(
                headerText = headerText,
                headerColor = headerColor,
                errorText = errorText
            )
        }
        ChirpSurface(
            modifier = Modifier
                .weight(1f),
        ) {
            formContent()
        }
    }
}

@Composable
private fun ChirpMobilePortraitLayout(
    logo: @Composable (() -> Unit),
    headerText: String,
    headerColor: Color,
    errorText: String?,
    formContent: @Composable (ColumnScope.() -> Unit),
    modifier: Modifier
) {
    ChirpSurface(
        modifier = modifier
            .consumeWindowInsets(insets = WindowInsets.navigationBars)
            .consumeWindowInsets(insets = WindowInsets.displayCutout),
        header = {
            Spacer(Modifier.height(32.dp))
            logo()
            Spacer(Modifier.height(32.dp))
        },
        content = {
            Spacer(Modifier.height(24.dp))
            AuthHeaderSection(
                headerText = headerText,
                headerColor = headerColor,
                errorText = errorText
            )
            Spacer(Modifier.height(24.dp))
            formContent()
        }
    )
}

@Composable
private fun ColumnScope.AuthHeaderSection(
    headerText: String,
    headerColor: Color,
    errorText: String? = null
) {
    Text(
        text = headerText,
        style = MaterialTheme.typography.titleLarge,
        color = headerColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    AnimatedVisibility(
        visible = errorText != null
    ) {
        if (errorText != null) {
            Text(
                text = errorText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun ChirpAdaptiveFormLayoutPreviewLight() {
    ChirpAdaptiveFormLayoutPreview(darkTheme = false)
}

@Composable
@Preview
fun ChirpAdaptiveFormLayoutPreviewDark() {
    ChirpAdaptiveFormLayoutPreview(darkTheme = true)
}

@Composable
private fun ChirpAdaptiveFormLayoutPreview(darkTheme: Boolean) {
    ChirpTheme(darkTheme = darkTheme) {
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
