package lt.vitalijus.core.designsystem.components.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import lt.vitalijus.core.designsystem.components.brand.ChirpSuccessIcon
import lt.vitalijus.core.designsystem.components.buttons.ChirpButton
import lt.vitalijus.core.designsystem.components.buttons.ChirpButtonStyle
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import lt.vitalijus.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpSimpleSuccessLayout(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    primaryButton: @Composable () -> Unit,
    secondaryButton: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-25).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.extended.textPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(height = 8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.extended.textSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(height = 24.dp))
            primaryButton()

            if (secondaryButton != null) {
                Spacer(modifier = Modifier.height(height = 8.dp))
                secondaryButton()
            }

            Spacer(modifier = Modifier.height(height = 8.dp))
        }
    }
}

@Composable
fun ChirpSimpleSuccessLayoutPreview(darkTheme: Boolean) {
    ChirpTheme(
        darkTheme = darkTheme
    ) {
        ChirpSimpleSuccessLayout(
            title = "Title",
            description = "Description",
            icon = {
                ChirpSuccessIcon()
            },
            primaryButton = {
                ChirpButton(
                    text = "Login",
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = "Resend verification email",
                    onClick = { },
                    style = ChirpButtonStyle.SECONDARY,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        )
    }
}

@Composable
@Preview(
    showBackground = true
)
fun ChirpSimpleSuccessLayoutPreviewLight() {
    ChirpSimpleSuccessLayoutPreview(darkTheme = false)
}

@Composable
@Preview
fun ChirpSimpleSuccessLayoutPreviewDark() {
    ChirpSimpleSuccessLayoutPreview(darkTheme = true)
}
