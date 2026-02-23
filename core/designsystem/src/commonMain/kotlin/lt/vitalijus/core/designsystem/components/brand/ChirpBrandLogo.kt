package lt.vitalijus.core.designsystem.components.brand

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chirp.core.designsystem.generated.resources.Res
import chirp.core.designsystem.generated.resources.logo_chirp
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpBrandLogo(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = vectorResource(Res.drawable.logo_chirp),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

@Composable
@Preview
fun ChirpBrandLogoPreviewLight() {
    ChirpTheme(
        darkTheme = false
    ) {
        ChirpBrandLogo()
    }
}

@Composable
@Preview
fun ChirpBrandLogoPreviewDark() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpBrandLogo()
    }
}
