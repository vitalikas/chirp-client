package lt.vitalijus.auth.presentation.register

sealed interface RegisterEvent {
    data class Success(val email: String): RegisterEvent
}
