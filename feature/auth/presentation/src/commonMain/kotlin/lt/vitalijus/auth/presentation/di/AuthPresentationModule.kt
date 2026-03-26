package lt.vitalijus.auth.presentation.di

import lt.vitalijus.auth.presentation.email_verification.EmailVerificationViewModel
import lt.vitalijus.auth.presentation.login.LoginViewModel
import lt.vitalijus.auth.presentation.register.RegisterViewModel
import lt.vitalijus.auth.presentation.register_success.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
}
