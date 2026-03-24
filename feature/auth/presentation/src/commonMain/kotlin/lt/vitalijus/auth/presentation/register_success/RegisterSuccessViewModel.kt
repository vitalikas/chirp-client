package lt.vitalijus.auth.presentation.register_success

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lt.vitalijus.auth.presentation.navigation.AuthGraphRoutes
import lt.vitalijus.core.domain.auth.AuthService
import lt.vitalijus.core.domain.util.onFailure
import lt.vitalijus.core.domain.util.onSuccess
import lt.vitalijus.core.presentation.util.toUiText

class RegisterSuccessViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val email = savedStateHandle.get<String>("email")
        ?: throw IllegalArgumentException("Email is required")

    private val _state = MutableStateFlow(
        RegisterSuccessState(
            registeredEmail = email
        )
    )
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
            initialValue = RegisterSuccessState()
        )

    private val eventChannel = Channel<RegisterSuccessEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: RegisterSuccessAction) {
        when (action) {
            RegisterSuccessAction.OnLoginClick -> Unit
            RegisterSuccessAction.OnResendVerificationEmailClick -> resendVerificationEmail()
        }
    }

    private fun resendVerificationEmail() {
        if (state.value.isResendingVerificationEmail) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(isResendingVerificationEmail = true)
            }
            authService.resendVerificationEmail(email = email)
                .onSuccess {
                    _state.update {
                        it.copy(
                            isResendingVerificationEmail = false,
                            resendVerificationError = null
                        )
                    }
                    eventChannel.send(RegisterSuccessEvent.ResendVerificationEmailSuccess)
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isResendingVerificationEmail = false,
                            resendVerificationError = error.toUiText()
                        )
                    }
                }
        }
    }
}
