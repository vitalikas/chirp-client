package lt.vitalijus.core.domain.auth

import lt.vitalijus.core.domain.util.DataError
import lt.vitalijus.core.domain.util.EmptyResult
import lt.vitalijus.core.domain.util.Result

interface AuthService {
    suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Remote>
}
