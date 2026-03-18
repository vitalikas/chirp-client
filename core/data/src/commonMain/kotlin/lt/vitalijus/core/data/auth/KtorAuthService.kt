package lt.vitalijus.core.data.auth

import io.ktor.client.HttpClient
import lt.vitalijus.core.data.dto.requests.RegisterRequest
import lt.vitalijus.core.data.networking.post
import lt.vitalijus.core.domain.auth.AuthService
import lt.vitalijus.core.domain.util.DataError
import lt.vitalijus.core.domain.util.EmptyResult

class KtorAuthService(
    private val httpClient: HttpClient
) : AuthService {

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body = RegisterRequest(
                username = username,
                email = email,
                password = password
            )
        )
    }
}
