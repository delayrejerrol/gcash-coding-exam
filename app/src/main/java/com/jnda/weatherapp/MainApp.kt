package com.jnda.weatherapp

import android.app.Application
import com.jnda.common.di.CommonComponent
import com.jnda.home.di.HomeComponent
import com.jnda.network.di.NetworkComponent
import com.jnda.signin.di.SignInComponent
import com.jnda.signup.di.SignUpComponent
import com.jnda.storage.di.StorageComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(
                CommonComponent().module,
                NetworkComponent().module,
                SignInComponent().module,
                SignUpComponent().module,
                HomeComponent().module,
                StorageComponent().module,
            )
        }
    }
}