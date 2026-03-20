package lt.vitalijus.chirp.di

import lt.vitalijus.auth.presentation.di.authPresentationModule
import lt.vitalijus.core.data.di.coreDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule
        )
    }
}
