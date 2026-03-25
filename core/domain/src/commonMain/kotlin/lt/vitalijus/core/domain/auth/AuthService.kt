package lt.vitalijus.core.domain.auth

import lt.vitalijus.core.domain.util.DataError
import lt.vitalijus.core.domain.util.EmptyResult

interface AuthService {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Remote>

    suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote>

    suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote>
}
