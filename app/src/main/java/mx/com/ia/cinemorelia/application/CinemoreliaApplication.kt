package mx.com.ia.cinemorelia.application

import android.app.Application
import mx.com.ia.cinemorelia.features.login.module.loginModule
import mx.com.ia.cinemorelia.module.cinemoreliaModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CinemoreliaApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val modules = listOf(
            cinemoreliaModule,
            loginModule
        )

        startKoin {
            modules(modules)
            androidContext(this@CinemoreliaApplication)
        }
    }
}