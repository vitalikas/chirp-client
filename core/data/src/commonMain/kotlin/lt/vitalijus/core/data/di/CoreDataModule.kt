package lt.vitalijus.core.data.di

import lt.vitalijus.core.data.auth.KtorAuthService
import lt.vitalijus.core.data.logging.KermitLogger
import lt.vitalijus.core.data.networking.HttpClientFactory
import lt.vitalijus.core.domain.auth.AuthService
import lt.vitalijus.core.domain.logging.ChirpLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformCoreDataModule: Module

val coreDataModule = module {
    includes(platformCoreDataModule)
    single<ChirpLogger> { KermitLogger }
    single {
        HttpClientFactory(chirpLogger = get()).create(engine = get())
    }
    single<AuthService> {
        KtorAuthService(httpClient = get())
    } // or singleOf(::KtorAuthService) bind AuthService::class
}
