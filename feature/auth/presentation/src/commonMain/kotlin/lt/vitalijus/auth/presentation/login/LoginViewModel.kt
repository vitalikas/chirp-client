package lt.vitalijus.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_email_not_verified
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lt.vitalijus.auth.domain.EmailValidator
import lt.vitalijus.core.domain.auth.AuthService
import lt.vitalijus.core.domain.util.DataError
import lt.vitalijus.core.domain.util.onFailure
import lt.vitalijus.core.domain.util.onSuccess
import lt.vitalijus.core.presentation.util.UiText
import lt.vitalijus.core.presentation.util.toUiText

class LoginViewModel(
    private val authService: AuthService
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeLoginStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private val isEmailValidFlow =
        snapshotFlow { state.value.emailTextFieldState.text.toString() }
            .map { EmailValidator.validate(email = it) }
            .distinctUntilChanged()

    private val isPasswordValidFlow =
        snapshotFlow { state.value.passwordTextFieldState.text.toString() }
            .map { it.isNotBlank() }
            .distinctUntilChanged()

    private val isLoggingInFlow = state
        .map { it.isLoggingIn }
        .distinctUntilChanged()


    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()

            LoginAction.OnTogglePasswordVisibility -> togglePasswordVisibility()

            else -> Unit
        }
    }

    private fun login() {
        if (!state.value.canLogin) return

        val email = state.value.emailTextFieldState.text.toString()
        val password = state.value.passwordTextFieldState.text.toString()

        viewModelScope.launch {
            _state.update {
                it.copy(isLoggingIn = true)
            }

            authService.login(
                email = email,
                password = password
            )
                .onSuccess { authInfo ->
                    _state.update {
                        it.copy(isLoggingIn = false)
                    }
                    eventChannel.send(LoginEvent.Success)
                }
                .onFailure { error ->
                    val errorMessage = when (error) {
                        DataError.Remote.UNAUTHORIZED -> UiText.Resource(Res.string.error_invalid_credentials)
                        DataError.Remote.FORBIDDEN -> UiText.Resource(Res.string.error_email_not_verified)
                        else -> error.toUiText()
                    }
                    _state.update {
                        it.copy(
                            isLoggingIn = false,
                            error = errorMessage
                        )
                    }
                }
        }
    }

    private fun togglePasswordVisibility() {
        _state.update {
            it.copy(isPasswordVisible = !it.isPasswordVisible)
        }
    }

    private fun observeLoginStates() {
        combine(
            isEmailValidFlow,
            isPasswordValidFlow,
            isLoggingInFlow
        ) { isEmailValid, isPasswordValid, isLoggingIn ->
            _state.update {
                it.copy(canLogin = !isLoggingIn && isEmailValid && isPasswordValid)
            }
        }
            .launchIn(viewModelScope)
    }
}
