package lt.vitalijus.core.data.mappers

import lt.vitalijus.core.data.dto.AuthInfoSerializable
import lt.vitalijus.core.data.dto.UserSerializable
import lt.vitalijus.core.domain.auth.AuthInfo
import lt.vitalijus.core.domain.auth.User

fun AuthInfoSerializable.toDomain(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user.toDomain()
    )
}

fun UserSerializable.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        hasEmailVerified = hasEmailVerified,
        profilePictureUrl = profilePictureUrl
    )
}
