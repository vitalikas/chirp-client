package lt.vitalijus.core.domain.auth

data class User(
    val id: String,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean,
    val profilePictureUrl: String? = null
)
