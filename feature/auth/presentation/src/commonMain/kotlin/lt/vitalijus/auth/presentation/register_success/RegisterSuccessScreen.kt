package lt.vitalijus.auth.presentation.register_success

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import chirp.feature.auth.presentation.generated.resources.success_account_created
import chirp.feature.auth.presentation.generated.resources.verification_email_resend_success
import chirp.feature.auth.presentation.generated.resources.verification_email_sent_to_x
import lt.vitalijus.core.designsystem.components.brand.ChirpSuccessIcon
import lt.vitalijus.core.designsystem.components.buttons.ChirpButton
import lt.vitalijus.core.designsystem.components.buttons.ChirpButtonStyle
import lt.vitalijus.core.designsystem.components.layouts.ChirpAdaptiveResultLayout
import lt.vitalijus.core.designsystem.components.layouts.ChirpSimpleResultLayout
import lt.vitalijus.core.designsystem.components.layouts.ChirpSnackbarScaffold
import lt.vitalijus.core.designsystem.theme.ChirpTheme
import lt.vitalijus.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessScreenRoot(
    viewModel: RegisterSuccessViewModel = koinViewModel(),
    onLoginClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            RegisterSuccessEvent.ResendVerificationEmailSuccess -> {
                snackbarHostState.showSnackbar(
                    message = getString(
                        Res.string.verification_email_resend_success
                    )
                )
            }
        }
    }

    RegisterSuccessScreen(
        state = state,
        onAction = { action ->
            when (action) {
                RegisterSuccessAction.OnLoginClick -> onLoginClick()
                else -> Unit
            }
            viewModel.onAction(action = action)
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun RegisterSuccessScreen(
    state: RegisterSuccessState,
    onAction: (RegisterSuccessAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    ChirpSnackbarScaffold(
        snackbarHostState = snackbarHostState
    ) {
        ChirpAdaptiveResultLayout {
            ChirpSimpleResultLayout(
                title = stringResource(Res.string.success_account_created),
                description = stringResource(
                    Res.string.verification_email_sent_to_x,
                    state.registeredEmail
                ),
                icon = {
                    ChirpSuccessIcon()
                },
                primaryButton = {
                    ChirpButton(
                        text = stringResource(Res.string.login),
                        onClick = { onAction(RegisterSuccessAction.OnLoginClick) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                secondaryButton = {
                    ChirpButton(
                        text = stringResource(Res.string.resend_verification_email),
                        onClick = { onAction(RegisterSuccessAction.OnResendVerificationEmailClick) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = !state.isResendingVerificationEmail,
                        isLoading = state.isResendingVerificationEmail,
                        style = ChirpButtonStyle.SECONDARY
                    )
                },
                secondaryError = state.resendVerificationError?.asString()
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        RegisterSuccessScreen(
            state = RegisterSuccessState(
                registeredEmail = "test@preview.com"
            ),
            onAction = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}
