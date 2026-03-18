package lt.vitalijus.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_invalid_email_format
import chirp.feature.auth.presentation.generated.resources.error_invalid_password_format
import chirp.feature.auth.presentation.generated.resources.error_invalid_username_format
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import lt.vitalijus.auth.domain.EmailValidator
import lt.vitalijus.core.domain.validation.PasswordValidator
import lt.vitalijus.core.presentation.util.UiText

class RegisterViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterState()
        )

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnInputTextFocusGain -> Unit
            RegisterAction.OnLoginClick -> validateFormInputs()
            RegisterAction.OnRegisterClick -> Unit
            RegisterAction.OnTogglePasswordVisibilityClick -> Unit
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
