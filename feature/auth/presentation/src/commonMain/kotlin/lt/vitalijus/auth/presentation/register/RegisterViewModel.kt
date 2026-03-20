package lt.vitalijus.auth.presentation.register

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_account_exists
import chirp.feature.auth.presentation.generated.resources.error_invalid_email_format
import chirp.feature.auth.presentation.generated.resources.error_invalid_password_format
import chirp.feature.auth.presentation.generated.resources.error_invalid_username_format
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
import lt.vitalijus.core.domain.validation.PasswordValidator
import lt.vitalijus.core.presentation.util.UiText
import lt.vitalijus.core.presentation.util.toUiText

class RegisterViewModel(
    private val authService: AuthService
) : ViewModel() {

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()


    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeValidationStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterState()
        )

    private val isUsernameValidFlow = snapshotFlow { state.value.usernameTextState.text.toString() }
        .map { username -> username.length in 3..20 }
        .distinctUntilChanged()

    private val isEmailValidFlow = snapshotFlow { state.value.emailTextState.text.toString() }
        .map { email -> EmailValidator.validate(email = email) }
        .distinctUntilChanged()

    private val isPasswordValidFlow = snapshotFlow { state.value.passwordTextState.text.toString() }
        .map { password -> PasswordValidator.validate(password = password).isValidPassword }
        .distinctUntilChanged()

    private val isRegistering = state
        .map { it.isRegistering }
        .distinctUntilChanged()

    private fun observeValidationStates() {
        combine(
            isUsernameValidFlow,
            isEmailValidFlow,
            isPasswordValidFlow,
            isRegistering
        ) { isUsernameValid, isEmailValid, isPasswordValid, isRegistering ->
            _state.update {
                it.copy(
                    canRegister = !isRegistering && isUsernameValid && isEmailValid && isPasswordValid
                )
            }
        }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnInputTextFocusGain -> Unit
            RegisterAction.OnLoginClick -> Unit
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> togglePasswordVisibility()
        }
    }

    private fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun register() {
        if (!validateFormInputs()) return

        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true) }

            val currentState = _state.value
            val email = currentState.emailTextState.text.toString()
            val username = currentState.usernameTextState.text.toString()
            val password = currentState.passwordTextState.text.toString()

            authService
                .register(
                    username = username,
                    email = email,
                    password = password
                )
                .onSuccess {
                    _state.update { it.copy(isRegistering = false) }
                }
                .onFailure { error ->
                    val registrationError = when (error) {
                        DataError.Remote.CONFLICT -> UiText.Resource(Res.string.error_account_exists)
                        else -> error.toUiText()
                    }
                    _state.update {
                        it.copy(
                            isRegistering = false,
                            registrationError = registrationError
                        )
                    }
                }
        }
    }

    private fun clearAllTextFieldErrors() {
        _state.update {
            it.copy(
                emailError = null,
                usernameError = null,
                passwordError = null,
                registrationError = null
            )
        }
    }

    private fun validateFormInputs(): Boolean {
        val currentState = _state.value
        val email = currentState.emailTextState.text.toString()
        val username = currentState.usernameTextState.text.toString()
        val password = currentState.passwordTextState.text.toString()

        val isEmailValid = EmailValidator.validate(email = email)
        val isUsernameValid = username.length in 3..20
        val passwordValidationState = PasswordValidator.validate(password = password)

        val emailError = if (!isEmailValid) {
            UiText.Resource(Res.string.error_invalid_email_format)
        } else null

        val usernameError = if (!isUsernameValid) {
            UiText.Resource(Res.string.error_invalid_username_format)
        } else null

        val passwordError = if (!passwordValidationState.isValidPassword) {
            UiText.Resource(Res.string.error_invalid_password_format)
        } else null

        _state.update {
            it.copy(
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError
            )
        }

        return isEmailValid && isUsernameValid && passwordValidationState.isValidPassword
    }
}
