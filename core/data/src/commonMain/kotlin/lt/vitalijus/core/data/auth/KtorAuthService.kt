package lt.vitalijus.core.data.auth

import io.ktor.client.HttpClient
import lt.vitalijus.core.data.dto.AuthInfoSerializable
import lt.vitalijus.core.data.dto.requests.EmailRequest
import lt.vitalijus.core.data.dto.requests.LoginRequest
import lt.vitalijus.core.data.dto.requests.RegisterRequest
import lt.vitalijus.core.data.mappers.toDomain
import lt.vitalijus.core.data.networking.get
import lt.vitalijus.core.data.networking.post
import lt.vitalijus.core.domain.auth.AuthInfo
import lt.vitalijus.core.domain.auth.AuthService
import lt.vitalijus.core.domain.util.DataError
import lt.vitalijus.core.domain.util.EmptyResult
import lt.vitalijus.core.domain.util.Result
import lt.vitalijus.core.domain.util.map

class KtorAuthService(
    private val httpClient: HttpClient
) : AuthService {

    override suspend fun login(
        email: String,
        password: String
    ): Result<AuthInfo, DataError.Remote> {
        return httpClient.post<LoginRequest, AuthInfoSerializable>(
            route = "/auth/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        ).map { authInfoSerializable ->
            authInfoSerializable.toDomain()
        }
    }

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

    override suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/resend-verification",
            body = EmailRequest(email = email)
        )
    }

    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get(
            route = "/auth/verify",
            queryParams = mapOf("token" to token)
        )
    }
}
