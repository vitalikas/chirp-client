package lt.vitalijus.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSerializable(
    val id: String,
    val username: String,
    val email: String,
    @SerialName("hasVerifiedEmail")
    val hasEmailVerified: Boolean,
    val profilePictureUrl: String? = null
)
