package lt.vitalijus.auth.presentation.login

sealed interface LoginEvent {
    data object Success : LoginEvent
    data object Error : LoginEvent
}
