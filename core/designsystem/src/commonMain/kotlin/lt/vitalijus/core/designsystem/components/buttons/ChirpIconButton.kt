package lt.vitalijus.core.designsystem.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import lt.vitalijus.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier
            .size(size = 45.dp),
        shape = RoundedCornerShape(size = 8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        colors = IconButtonDefaults.outlinedIconButtonColors(
            contentColor = MaterialTheme.colorScheme.surface,
            containerColor = MaterialTheme.colorScheme.extended.textSecondary
        ),
        content = content
    )
}

@Composable
@Preview
fun ChirpIconButtonPreviewLight() {
    ChirpTheme(
        darkTheme = false
    ) {
        ChirpIconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun ChirpIconButtonPreviewDark() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChirpIconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
    }
}
